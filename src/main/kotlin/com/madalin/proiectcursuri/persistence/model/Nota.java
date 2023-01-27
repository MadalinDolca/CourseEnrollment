package com.madalin.proiectcursuri.persistence.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "nota")
@ToString
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    public int id;

    @Column(nullable = false)
    public int valoare;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "cursant_id_fk")
    public Cursant cursantId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "curs_id_fk")
    public Curs cursId;

    public Nota(Cursant cursantId, Curs cursId) {
        this.cursantId = cursantId;
        this.cursId = cursId;
    }

    public Nota(int valoare, Cursant cursantId, Curs cursId) {
        this.valoare = valoare;
        this.cursantId = cursantId;
        this.cursId = cursId;
    }
}