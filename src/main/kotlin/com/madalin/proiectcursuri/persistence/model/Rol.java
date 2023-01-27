package com.madalin.proiectcursuri.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    public int id;

    @Column(nullable = false, unique = true)
    public String numeRol;

    @OneToMany(mappedBy = "rolId")
    public List<Cursant> cursanti;

    @OneToMany(mappedBy = "rolId")
    public List<Profesor> profesori;

    public Rol(String numeRol) {
        this.numeRol = numeRol;
    }
}
