package com.madalin.proiectcursuri.service

import com.madalin.proiectcursuri.dto.*

interface IAdminService {
    fun getAllPersoane(tabel: String, coloana: String): List<PersoanaDTO>
    fun getPersoanaById(tabel: String, id: Long): PersoanaDTO
    fun getPersoaneByEmail(email: String): List<PersoanaDTO>
    fun getPersoaneByRole(id: Int): List<PersoanaDTO>
    fun getPersoaneByCurs(tabel: String, id: Int): List<PersoanaDTO>
    fun getCursuriPersoana(tabel: String, id: Long): List<CursDTO>
    fun getAllCursuri(): List<CursDTO>
    fun getCursById(id: Int): CursDTO
    fun getAllRoluri(): List<RolDTO>
    fun addPersoana(persoanaDto: PersoanaDTO): PersoanaDTO
    fun addCurs(cursDto: CursDTO): CursDTO
    fun updatePersoana(id: Long, updateDto: PersoanaUpdateDTO): PersoanaDTO
    fun joinPersoanaLaCurs(tabel: String, id: Long, cursID: Int): List<PersoanaDTO>
    fun unjoinPersoanaDeLaCurs(tabel: String, id: Long, cursID: Int): List<PersoanaDTO>
    fun deletePersoana(id: Long)
    fun deleteCurs(id: Int)
}