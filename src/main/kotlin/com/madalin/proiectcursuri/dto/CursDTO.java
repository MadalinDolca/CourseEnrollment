package com.madalin.proiectcursuri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CursDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public int id;

    @NotEmpty
    @Size(min = 1, max = 50)
    public String numeCurs;
}
