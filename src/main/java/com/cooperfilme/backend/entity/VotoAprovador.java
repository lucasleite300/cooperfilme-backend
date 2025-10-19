package com.cooperfilme.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "voto_aprovador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoAprovador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "roteiro_id", nullable = false)
    private Roteiro roteiro;

    @ManyToOne
    @JoinColumn(name = "aprovador_id")
    private Usuario aprovador;

    private Boolean voto;

    @Column(name = "data_voto")
    private LocalDateTime dataVoto = LocalDateTime.now();
}
