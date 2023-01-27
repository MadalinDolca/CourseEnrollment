package com.madalin.proiectcursuri.persistence.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin extends Persoana {
    public Admin(Long id, String prenume, String nume, String email, String parola, Rol rolId) {
        super(prenume, nume, email, parola, rolId);
        this.id = id;
    }
}
