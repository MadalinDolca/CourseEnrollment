package com.madalin.proiectcursuri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PersoanaDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @NotEmpty
    @Size(min = 1, max = 50)
    public String prenume;

    @NotEmpty
    @Size(min = 1, max = 50)
    public String nume;

    @NotEmpty
    @Email
    public String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Size(min = 8, max = 30)
    public String parola;

    public int rolId;
}
