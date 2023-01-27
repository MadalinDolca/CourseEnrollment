package com.madalin.proiectcursuri.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CursantNotaCursDTO {
    public String cursantId;
    public String cursantNume;
    public String cursantPrenume;
    public int cursId;
    public String cursNume;
    public int profesorId;
    public String profesorNume;
    public int valoare;

    public CursantNotaCursDTO(String cursantId, String cursantNume, String cursantPrenume, int cursId, String cursNume, int profesorId, String profesorNume, int valoare) {
        this.cursantId = cursantId;
        this.cursantNume = cursantNume;
        this.cursantPrenume = cursantPrenume;
        this.cursId = cursId;
        this.cursNume = cursNume;
        this.profesorId = profesorId;
        this.profesorNume = profesorNume;
        this.valoare = valoare;
    }
}
