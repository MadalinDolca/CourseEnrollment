package com.madalin.proiectcursuri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NotaDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String cursId;

    @Min(1)
    @Max(10)
    public int valoare;
}