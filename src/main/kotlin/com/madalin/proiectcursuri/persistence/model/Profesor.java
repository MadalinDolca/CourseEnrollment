package com.madalin.proiectcursuri.persistence.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profesor")
@ToString
public class Profesor extends Persoana {
    @ManyToMany
    @JoinTable(
            name = "profesor_curs_list",
            joinColumns = @JoinColumn(name = "profesor_id"),
            inverseJoinColumns = @JoinColumn(name = "curs_id")
    )
    public Set<Curs> listaCursuri;

    public boolean adaugaCurs(Curs curs) {
        return this.listaCursuri.add(curs);
    }

    public boolean stergeCurs(Curs curs) {
        return this.listaCursuri.remove(curs);
    }

    public Profesor(Long id, Set<Curs> listaCursuri, String prenume, String nume, String email, String parola, Rol rolId) {
        super(prenume, nume, email, parola, rolId);
        this.id = id;
        this.listaCursuri = listaCursuri;
    }
}
