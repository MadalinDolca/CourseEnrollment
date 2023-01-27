package com.madalin.proiectcursuri.security

import com.madalin.proiectcursuri.exception.NegasitException
import com.madalin.proiectcursuri.persistence.repository.IPersoanaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service // indica bean-ului faptul ca detine logica de afaceri
class PersoanaDetailsService(
    private val personRepository: IPersoanaRepository
) : UserDetailsService {

    // obtine si returneaza persoana din baza de date cu username-ul (email-ul) dat intr-un UserDetails
    override fun loadUserByUsername(username: String): UserDetails {
        val persoana = personRepository.findByEmail(username).orElseThrow { NegasitException("Persoana negasita") }

        return PersoanaAuthDetails(persoana)
    }
}