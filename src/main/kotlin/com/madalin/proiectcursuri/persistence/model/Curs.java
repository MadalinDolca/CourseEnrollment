package com.madalin.proiectcursuri.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "curs")
public class Curs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    public int id;

    @Column(nullable = false, unique = true)
    public String numeCurs;

    @ManyToMany(mappedBy = "listaCursuri")
    public List<Cursant> listaCursanti = new ArrayList<>();

    @ManyToMany(mappedBy = "listaCursuri")
    public List<Profesor> listaProfesori = new ArrayList<>();

    @OneToMany(mappedBy = "cursId")
    public List<Nota> listaNote;

    public Curs(String numeCurs) {
        this.numeCurs = numeCurs;
    }
}
