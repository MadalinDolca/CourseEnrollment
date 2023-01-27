package com.madalin.proiectcursuri.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persoana")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SuperBuilder
public class Persoana {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, updatable = false)
    public Long id;

    @Column(nullable = false)
    public String prenume;

    @Column(nullable = false)
    public String nume;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String parola;

    @ManyToOne
    @JoinColumn(name = "rol_id_fk")
    public Rol rolId;

    public Persoana(String prenume, String nume, String email, String parola, Rol rolId) {
        this.prenume = prenume;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
        this.rolId = rolId;
    }
}