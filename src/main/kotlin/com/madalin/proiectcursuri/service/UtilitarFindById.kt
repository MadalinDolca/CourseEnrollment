package com.madalin.proiectcursuri.service

import com.madalin.proiectcursuri.exception.NegasitException
import com.madalin.proiectcursuri.persistence.model.*
import com.madalin.proiectcursuri.persistence.repository.*

// companion pentru utilizarea metodelor de obtinere a datelor din tabele in functie de ID
object UtilitarFindById {
    fun findPersoanaById(id: Long, persoanaRepository: IPersoanaRepository): Persoana {
        return persoanaRepository.findById(id).orElseThrow { NegasitException("Persoana negasita") }
    }

    fun findCursantById(id: Long, cursantRepository: ICursantRepository): Cursant {
        return cursantRepository.findById(id).orElseThrow { NegasitException("Cursant negasit") }
    }

    fun findProfesorById(id: Long, profesorRepository: IProfesorRepository): Profesor {
        return profesorRepository.findById(id).orElseThrow { NegasitException("Profesor negasit") }
    }

    fun findAdminById(id: Long, adminRepository: IAdminRepository): Admin {
        return adminRepository.findById(id).orElseThrow { NegasitException("Admin negasit") }
    }

    fun findCursById(id: Int, cursRepository: ICursRepository): Curs {
        return cursRepository.findById(id).orElseThrow { NegasitException("Curs negasit") }
    }

    fun findRolById(id: Int, rolRepository: IRolRepository): Rol {
        return rolRepository.findById(id).orElseThrow { NegasitException("Rol negasit") }
    }
}