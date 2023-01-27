package com.madalin.proiectcursuri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
//@NoArgsConstructor
public class AdminDTO extends PersoanaDTO {
}
