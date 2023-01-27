package com.madalin.proiectcursuri.persistence.repository

import com.madalin.proiectcursuri.persistence.model.Nota
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface INotaRepository : JpaRepository<Nota, Int> {
    @Query(
        value = "SELECT * FROM nota " +
                "WHERE nota.cursant_id_fk = :cursantId " +
                "AND nota.curs_id_fk = :cursId", nativeQuery = true
    )
    fun findByCursantAndCurs(cursantId: Long, cursId: Int): Optional<Nota>
}