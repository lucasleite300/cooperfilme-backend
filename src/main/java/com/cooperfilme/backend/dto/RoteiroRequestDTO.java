package com.cooperfilme.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoteiroRequestDTO {

    @NotBlank(message = "O campo 'titulo' é obrigatório")
    private String titulo;

    @NotBlank(message = "O campo 'texto' é obrigatório")
    private String texto;

    @NotBlank(message = "O campo 'nomeAutor' é obrigatório")
    private String nomeAutor;

    @NotBlank(message = "O campo 'emailAutor' é obrigatório")
    @Email(message = "O campo 'emailAutor' deve ser um e-mail válido")
    private String emailAutor;
}
