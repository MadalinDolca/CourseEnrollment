package com.madalin.proiectcursuri.persistence.repository

import com.madalin.proiectcursuri.persistence.model.Curs
import javax.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ICursRepository : JpaRepository<Curs, Int> {
    @Query(
        value = "SELECT * FROM curs " +
                "WHERE UPPER(curs.nume_curs) = UPPER(:numeCurs)", nativeQuery = true
    )
    fun findByNumeCurs(numeCurs: String): Optional<Curs>

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = "DELETE FROM cursant_curs_list " +
                "WHERE curs_id = :cursId", nativeQuery = true
    )
    fun deleteFromCursanti(cursId: Int)

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = "DELETE FROM profesor_curs_list " +
                "WHERE curs_id = :cursId", nativeQuery = true
    )
    fun deleteFromProfesori(cursId: Int)

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = "DELETE FROM curs " +
                "WHERE curs.id = :cursId", nativeQuery = true
    )
    fun deleteCurs(cursId: Int)
}