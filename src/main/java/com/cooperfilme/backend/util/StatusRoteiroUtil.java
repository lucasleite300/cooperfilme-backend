package com.cooperfilme.backend.util;

import com.cooperfilme.backend.entity.enums.StatusRoteiro;

import java.util.*;

public class StatusRoteiroUtil {

    private static final Map<StatusRoteiro, List<StatusRoteiro>> fluxos = new HashMap<>();

    static {
        fluxos.put(StatusRoteiro.AGUARDANDO_ANALISE, List.of(StatusRoteiro.EM_ANALISE));
        fluxos.put(StatusRoteiro.EM_ANALISE, List.of(StatusRoteiro.AGUARDANDO_REVISAO, StatusRoteiro.RECUSADO));
        fluxos.put(StatusRoteiro.AGUARDANDO_REVISAO, List.of(StatusRoteiro.EM_REVISAO));
        fluxos.put(StatusRoteiro.EM_REVISAO, List.of(StatusRoteiro.AGUARDANDO_APROVACAO));
    }

    public static boolean isTransicaoValida(StatusRoteiro atual, StatusRoteiro proximo) {
        return fluxos.getOrDefault(atual, Collections.emptyList()).contains(proximo);
    }
}

