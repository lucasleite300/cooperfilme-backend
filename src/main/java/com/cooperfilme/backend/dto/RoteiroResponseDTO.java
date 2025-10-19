package com.cooperfilme.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoteiroResponseDTO {
    private Long id;
    private String protocolo;
    private String titulo;
    private String status;
    private String nomeAutor;
    private String dataEnvio;
    private String nomeResponsavelAtual;
}
