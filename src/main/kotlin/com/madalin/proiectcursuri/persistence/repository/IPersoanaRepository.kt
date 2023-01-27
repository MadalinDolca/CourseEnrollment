package com.madalin.proiectcursuri.persistence.repository

import com.madalin.proiectcursuri.persistence.model.Persoana
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface IPersoanaRepository : JpaRepository<Persoana, Long> {
    fun findByEmail(email: String): Optional<Persoana>
}