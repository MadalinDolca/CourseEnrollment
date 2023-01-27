package com.madalin.proiectcursuri.persistence.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginViewModel {
    public String email;
    public String parola;

    public String getUsername() {
        return this.email;
    }
}