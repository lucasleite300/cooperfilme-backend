package com.cooperfilme.backend.repository.spec;

import com.cooperfilme.backend.entity.Roteiro;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// RoteiroSpecification.java
public class RoteiroSpecification {
    public static Specification<Roteiro> comFiltros(
            String status, LocalDateTime dataInicio, LocalDateTime dataFim, String emailUsuario) {

        return (root, query, cb) -> {
            List<Predicate> preds = new ArrayList<>();

            if (status != null) {
                preds.add(cb.equal(root.get("status").get("nome"), status));
            }
            if (emailUsuario != null) {
                preds.add(cb.equal(root.get("emailAutor"), emailUsuario));
            }
            if (dataInicio != null) {
                preds.add(cb.greaterThanOrEqualTo(root.get("dataEnvio"), dataInicio));
            }
            if (dataFim != null) {
                preds.add(cb.lessThanOrEqualTo(root.get("dataEnvio"), dataFim));
            }

            query.orderBy(cb.desc(root.get("dataEnvio")));
            return cb.and(preds.toArray(new Predicate[0]));
        };
    }
}

