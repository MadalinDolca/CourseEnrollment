package com.madalin.proiectcursuri.persistence.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cursant")
public class Cursant extends Persoana {
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "cursant_curs_list",
            joinColumns = @JoinColumn(name = "cursant_id"),
            inverseJoinColumns = @JoinColumn(name = "curs_id")
    )
    public Set<Curs> listaCursuri;

    @OneToMany(mappedBy = "cursantId")
    public List<Nota> listaNote;

    public Cursant(Long id, Set<Curs> listaCursuri, String prenume, String nume, String email, String parola, Rol rolId) {
        super(prenume, nume, email, parola, rolId);
        this.id = id;
        this.listaCursuri = listaCursuri;
    }

    public boolean adaugaCurs(Curs curs) {
        return this.listaCursuri.add(curs);
    }

    public boolean stergeCurs(Curs curs) {
        return this.listaCursuri.remove(curs);
    }
}