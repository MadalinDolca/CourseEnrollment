package com.madalin.proiectcursuri.service

import com.madalin.proiectcursuri.dto.*

interface IProfesorService {
    fun getDateProfesor(email: String): ProfesorDTO
    fun getCursantiByCurs(profesorId: Long, cursId: Int): List<PersoanaDTO> // List<CursantNotaCursDTO>
    fun updateNota(profesorId: Long, cursantId: Long, cursId: Int, valoareNoua: Int): List<NotaDTO>
    fun updateProfesor(id: Long, persoanaUpdateDto: PersoanaUpdateDTO): PersoanaDTO
    fun getCursuriProfesor(id: Long): Set<CursDTO>
}
