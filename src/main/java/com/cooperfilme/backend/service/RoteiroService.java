package com.cooperfilme.backend.service;

import com.cooperfilme.backend.dto.RoteiroRequestDTO;
import com.cooperfilme.backend.dto.RoteiroResponseDTO;
import com.cooperfilme.backend.dto.RoteiroStatusResponseDTO;
import com.cooperfilme.backend.entity.*;
import com.cooperfilme.backend.entity.enums.StatusRoteiro;
import com.cooperfilme.backend.repository.*;
import com.cooperfilme.backend.repository.spec.RoteiroSpecification;
import com.cooperfilme.backend.util.StatusRoteiroUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RoteiroService {

    private final RoteiroRepository roteiroRepository;
    private final UsuarioRepository usuarioRepository;
    private final StatusRepository statusRepository;
    private final VotoRoteiroRepository votoRoteiroRepository;

    public RoteiroService(
            RoteiroRepository roteiroRepository,
            UsuarioRepository usuarioRepository,
            StatusRepository statusRepository,
            VotoRoteiroRepository votoRoteiroRepository) {
        this.roteiroRepository = roteiroRepository;
        this.usuarioRepository = usuarioRepository;
        this.statusRepository = statusRepository;
        this.votoRoteiroRepository = votoRoteiroRepository;
    }

    @Transactional
    public RoteiroResponseDTO cadastrarRoteiro(RoteiroRequestDTO dto) {
        Roteiro roteiro = new Roteiro();

        roteiro.setTitulo(dto.getTitulo());
        roteiro.setTexto(dto.getTexto());
        roteiro.setNomeAutor(dto.getNomeAutor());
        roteiro.setEmailAutor(dto.getEmailAutor());

        Status status = statusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Status não encontrado"));
        roteiro.setStatus(status);

        Roteiro salvo = roteiroRepository.save(roteiro);
        salvo.gerarProtocolo();
        roteiroRepository.save(salvo);

        return new RoteiroResponseDTO(
                salvo.getId(),
                salvo.getProtocolo(),
                salvo.getTitulo(),
                salvo.getStatus().getNome(),
                salvo.getNomeAutor(),
                salvo.getDataEnvio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                null
        );
    }

    @Transactional(readOnly = true)
    public RoteiroStatusResponseDTO buscarPorProtocolo(String protocolo) {
        Roteiro roteiro = roteiroRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new RuntimeException("Roteiro não encontrado para o protocolo: " + protocolo));

        return new RoteiroStatusResponseDTO(roteiro.getTitulo(), roteiro.getStatus().getNome());
    }

    @Transactional(readOnly = true)
    public List<RoteiroResponseDTO> buscarComFiltros(String status, LocalDateTime  dataInicio, LocalDateTime dataFim, String emailUsuario) {

        var spec = RoteiroSpecification.comFiltros(status, dataInicio, dataFim, emailUsuario);

        return roteiroRepository.findAll(spec)
                .stream()
                .map(r -> new RoteiroResponseDTO(
                        r.getId(),
                        r.getProtocolo(),
                        r.getTitulo(),
                        r.getStatus().getNome(),
                        r.getNomeAutor(),
                        r.getDataEnvio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        r.getUsuarioResponsavel() != null ? r.getUsuarioResponsavel().getNome() : null
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public RoteiroResponseDTO buscarPorId(Long id) {
        Roteiro roteiro = roteiroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Roteiro não encontrado"));

        return new RoteiroResponseDTO(
                roteiro.getId(),
                roteiro.getProtocolo(),
                roteiro.getTitulo(),
                roteiro.getStatus().getNome(),
                roteiro.getNomeAutor(),
                roteiro.getDataEnvio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                roteiro.getUsuarioResponsavel() != null ? roteiro.getUsuarioResponsavel().getNome() : null
        );
    }

    public void mudarStatus(Long id, int novoStatusCodigo) {
        Roteiro roteiro = roteiroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Roteiro não encontrado"));

        StatusRoteiro atual = StatusRoteiro.fromCodigo(roteiro.getStatus().getId());
        StatusRoteiro proximo = StatusRoteiro.fromCodigo(novoStatusCodigo);

        if (!StatusRoteiroUtil.isTransicaoValida(atual, proximo)) {
            throw new IllegalArgumentException("Transição de status inválida!");
        }

        Status status = statusRepository.findById((long) proximo.getCodigo()).orElseThrow(() -> new RuntimeException("Status não encontrado"));

        roteiro.setStatus(status);
        roteiro.setUsuarioResponsavel(getUsuarioLogado());
        roteiroRepository.save(roteiro);
    }

    @Transactional
    public void votar(Long roteiroId, boolean aprovado) {
        Roteiro roteiro = roteiroRepository.findById(roteiroId)
                .orElseThrow(() -> new IllegalArgumentException("Roteiro não encontrado"));

        if (!getUsuarioLogado().getPerfil().getNome().equals("APROVADOR")) {
            throw new IllegalArgumentException("Usuário não tem permissão para votar");
        }

        if (!roteiro.getStatus().getNome().equals(StatusRoteiro.AGUARDANDO_APROVACAO.toString().toLowerCase()) &&
                !roteiro.getStatus().getNome().equals(StatusRoteiro.EM_APROVACAO.toString().toLowerCase())) {
            throw new IllegalArgumentException("Roteiro não pode receber votos nesse status");
        }

        if (votoRoteiroRepository.existsByRoteiroIdAndAprovadorId(roteiroId, getUsuarioLogado().getId())) {
            throw new IllegalArgumentException("Usuário já votou nesse roteiro");
        }

        VotoAprovador voto = new VotoAprovador();
        voto.setRoteiro(roteiro);
        voto.setAprovador(getUsuarioLogado());
        voto.setVoto(aprovado);
        votoRoteiroRepository.save(voto);

        List<VotoAprovador> votos = votoRoteiroRepository.findByRoteiroId(roteiroId);

        if (votos.stream().anyMatch(v -> Boolean.FALSE.equals(v.getVoto()))) {
            Status status = statusRepository.findById((long) StatusRoteiro.RECUSADO.getCodigo()).orElseThrow(() -> new RuntimeException("Status não encontrado"));
            roteiro.setStatus(status);
        } else if (votos.size() >= 3 && votos.stream().allMatch(v -> Boolean.TRUE.equals(v.getVoto()))) {
            Status status = statusRepository.findById((long) StatusRoteiro.APROVADO.getCodigo()).orElseThrow(() -> new RuntimeException("Status não encontrado"));
            roteiro.setStatus(status);
        } else {
            Status status = statusRepository.findById((long) StatusRoteiro.EM_APROVACAO.getCodigo()).orElseThrow(() -> new RuntimeException("Status não encontrado"));
            roteiro.setStatus(status);
        }

        roteiro.setUsuarioResponsavel(getUsuarioLogado());

        roteiroRepository.save(roteiro);
    }


    public Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        String email = auth.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

}

