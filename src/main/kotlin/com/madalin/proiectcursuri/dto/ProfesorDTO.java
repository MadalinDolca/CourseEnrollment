package com.madalin.proiectcursuri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorDTO extends PersoanaDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Set<CursDTO> listaCursuri;
}