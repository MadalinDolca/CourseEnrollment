package com.madalin.proiectcursuri.stuff

import com.madalin.proiectcursuri.exception.ConflictException
import com.madalin.proiectcursuri.persistence.model.Persoana
import com.madalin.proiectcursuri.persistence.repository.IPersoanaRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component // permite Spring-ului sa detecteze automat bean-urile personalizate
class CheckAuth(private val persoanaRepository: IPersoanaRepository) {
    // verifica daca ID-ul dat corespunde cu cel al utilizatorului curent
    fun checkUserId(id: Long) {
        val email = SecurityContextHolder.getContext()
            .authentication
            .principal
            .toString()

        this.persoanaRepository.findByEmail(email)
            .ifPresent { persoana: Persoana -> // daca exista deja adresa de email
                if (persoana.id != id) { // verifica ID-urile
                    throw ConflictException("Acest ID nu este al tau")
                }
            }
    }
}