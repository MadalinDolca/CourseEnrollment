package com.madalin.proiectcursuri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CursantDTO extends PersoanaDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Set<CursDTO> listaCursuri = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public List<NotaDTO> listaNote = new ArrayList<>();
}
