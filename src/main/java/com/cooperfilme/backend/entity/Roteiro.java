package com.cooperfilme.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "roteiro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Roteiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String texto;

    private LocalDateTime dataEnvio = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "usuario_responsavel_id")
    private Usuario usuarioResponsavel;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    private String justificativa;

    private LocalDateTime dataUltimaAtualizacao = LocalDateTime.now();

    @Column(unique = true)
    private String protocolo;

    @Column(name = "nome_autor")
    private String nomeAutor;

    @Column(name = "email_autor")
    private String emailAutor;

    /**
     * Gera protocolo no formato: sequencial + mês + ano
     * Exemplo: "11025"
     */
    public void gerarProtocolo() {
        if (this.id != null && this.protocolo == null) {
            String sequencial = String.valueOf(this.id);
            String mesAno = DateTimeFormatter.ofPattern("MMyy").format(this.dataEnvio);
            this.protocolo = sequencial + mesAno;
        }
    }
}
