package com.madalin.proiectcursuri.persistence.repository

import com.madalin.proiectcursuri.dto.CursantNotaCursDTO
import com.madalin.proiectcursuri.persistence.model.Profesor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IProfesorRepository : JpaRepository<Profesor, Long> {
    fun findByEmail(email: String): Optional<Profesor>

    @Query(value = "SELECT * FROM profesor WHERE rol_id_fk = :rolId", nativeQuery = true)
    fun findByRolId(rolId: Int): List<Profesor>

    @Query(value = "SELECT * FROM profesor WHERE email LIKE %:email%", nativeQuery = true)
    fun findLikeEmail(email: String): List<Profesor>

    @Query(
        value = "SELECT cursant.id AS cursantId, cursant.nume AS cursantNume, cursant.prenume AS cursantPrenume, curs.id AS cursId, curs.nume_curs AS cursNume, profesor.id AS profesorId, profesor.nume as profesorNume, nota.valoare AS valoare\n" +
                "FROM cursant\n" +
                "         INNER JOIN cursant_curs_list ON cursant.id = cursant_curs_list.cursant_id\n" +
                "         INNER JOIN curs ON cursant_curs_list.curs_id = curs.id\n" +
                "         INNER JOIN profesor_curs_list ON curs.id = profesor_curs_list.curs_id\n" +
                "         INNER JOIN profesor ON profesor_curs_list.profesor_id = profesor.id\n" +
                "         INNER JOIN nota ON cursant.id = nota.cursant_id_fk\n" +
                "WHERE profesor.id = :profesorId AND curs.id = :cursId AND (nota.curs_id_fk = curs.id AND nota.cursant_id_fk = cursant.id)", nativeQuery = true
    )
    fun findCursantiNoteCurs(profesorId: Long, cursId: Int): List<CursantNotaCursDTO>
}