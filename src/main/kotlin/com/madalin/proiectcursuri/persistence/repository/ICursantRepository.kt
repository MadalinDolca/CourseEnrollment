package com.madalin.proiectcursuri.persistence.repository

import com.madalin.proiectcursuri.persistence.model.Cursant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ICursantRepository : JpaRepository<Cursant, Long> {
    fun findByEmail(email: String): Optional<Cursant>

    @Query(value = "SELECT * FROM cursant WHERE rol_id_fk = :rolId", nativeQuery = true)
    fun findByRolId(rolId: Int): List<Cursant>

    @Query(value = "SELECT * FROM cursant WHERE email LIKE %:email%", nativeQuery = true)
    fun findLikeEmail(email: String): List<Cursant>
}