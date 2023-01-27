package com.madalin.proiectcursuri.service

import com.madalin.proiectcursuri.dto.*

interface ICursantService {
    fun getDateCursant(email: String): CursantDTO
    fun getAllCursuri(): List<CursDTO>
    fun getNoteCursant(id: Long): List<NotaDTO>
    fun getCursuriCursant(id: Long): Set<CursDTO>
    fun signUp(cursantDTO: CursantDTO): CursantDTO
    fun updateCursant(id: Long, persoanaUpdateDto: PersoanaUpdateDTO): PersoanaDTO
    fun joinCurs(cursantId: Long, cursId: Int): Set<CursDTO>
    fun unjoinCurs(cursantId: Long, cursId: Int): Set<CursDTO>
}