package com.cooperfilme.backend.entity.enums;

import java.util.Arrays;

public enum StatusRoteiro {
    AGUARDANDO_ANALISE(1),
    EM_ANALISE(2),
    AGUARDANDO_REVISAO(3),
    EM_REVISAO(4),
    AGUARDANDO_APROVACAO(5),
    EM_APROVACAO(6),
    APROVADO(7),
    RECUSADO(8),
    ERRO(9);

    private final int codigo;

    StatusRoteiro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static StatusRoteiro fromCodigo(long codigo) {
        return Arrays.stream(values())
                .filter(s -> s.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código inválido: " + codigo));
    }
}

