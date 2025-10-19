package com.cooperfilme.backend.controller;

import com.cooperfilme.backend.dto.*;
import com.cooperfilme.backend.service.RoteiroService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/roteiros")
public class RoteiroController {

    private final RoteiroService roteiroService;

    public RoteiroController(RoteiroService roteiroService) {
        this.roteiroService = roteiroService;
    }

    @PostMapping
    public ResponseEntity<RoteiroResponseDTO> cadastrarRoteiro(@Valid @RequestBody RoteiroRequestDTO dto) {
        RoteiroResponseDTO response = roteiroService.cadastrarRoteiro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoteiroResponseDTO> visualizarRoteiro(@PathVariable Long id) {
        RoteiroResponseDTO roteiroDTO = roteiroService.buscarPorId(id);
        return ResponseEntity.ok(roteiroDTO);
    }

    @GetMapping("/status/{protocolo}")
    public ResponseEntity<RoteiroStatusResponseDTO> buscarPorProtocolo(@PathVariable String protocolo) {
        RoteiroStatusResponseDTO response = roteiroService.buscarPorProtocolo(protocolo);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RoteiroResponseDTO>> buscarComFiltros(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam(required = false) String emailUsuario) {

        List<RoteiroResponseDTO> response = roteiroService.buscarComFiltros(status, dataInicio, dataFim, emailUsuario);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> mudarStatus(
            @PathVariable Long id,
            @Valid @RequestBody MudarStatusRequest request) {

        roteiroService.mudarStatus(id, request.novoStatus());
        return ResponseEntity.ok("Status alterado com sucesso!");
    }

    @PatchMapping("/{id}/votar")
    public ResponseEntity<String> registrarVoto(
            @PathVariable Long id,
            @Valid @RequestBody VotoRequest votoRequest) {

        roteiroService.votar(id, votoRequest.aprovado());
        return ResponseEntity.ok("Voto registrado com sucesso!");
    }
}