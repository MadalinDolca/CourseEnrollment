package com.madalin.proiectcursuri.persistence.repository

import com.madalin.proiectcursuri.persistence.model.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IAdminRepository : JpaRepository<Admin, Long> {
    fun findByEmail(email: String): Optional<Admin>

    @Query(value = "SELECT * FROM admin WHERE rol_id_fk = :rolId", nativeQuery = true)
    fun findByRolId(rolId: Int): List<Admin>

    @Query(value = "SELECT * FROM admin WHERE email LIKE %:email%", nativeQuery = true)
    fun findLikeEmail(email: String): List<Admin>
}