package com.madalin.proiectcursuri.service

import com.madalin.proiectcursuri.stuff.CheckAuth
import com.madalin.proiectcursuri.stuff.IMainConverter
import com.madalin.proiectcursuri.dto.*
import com.madalin.proiectcursuri.exception.NegasitException
import com.madalin.proiectcursuri.exception.ValoareNepermisaException
import com.madalin.proiectcursuri.service.UtilitarFindById.findCursById
import com.madalin.proiectcursuri.service.UtilitarFindById.findCursantById
import com.madalin.proiectcursuri.service.UtilitarFindById.findProfesorById
import com.madalin.proiectcursuri.persistence.model.Curs
import com.madalin.proiectcursuri.persistence.model.Nota
import com.madalin.proiectcursuri.persistence.model.Profesor
import com.madalin.proiectcursuri.persistence.repository.ICursRepository
import com.madalin.proiectcursuri.persistence.repository.ICursantRepository
import com.madalin.proiectcursuri.persistence.repository.IProfesorRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

// serviciu pentru activitatea unui profesor
@Service // indica bean-ului faptul ca detine logica de afaceri
class ProfesorService(
    private val profesorRepository: IProfesorRepository,
    private val cursRepository: ICursRepository,
    private val cursantRepository: ICursantRepository,
    private val converter: IMainConverter,
    private val encoder: PasswordEncoder,
    private val checkAuth: CheckAuth
) : IProfesorService {
    // obtine datele profesorului
    override fun getDateProfesor(email: String): ProfesorDTO {
        val profesor = profesorRepository.findByEmail(email) // obtine datele din baza de date
        return converter.converter(profesor, ProfesorDTO::class.java) // returneaza datele profesorului
    }

    // returneaza o lista cu cursantii care sunt inscrisi la cursul dat
    override fun getCursantiByCurs(profesorId: Long, cursId: Int): List<PersoanaDTO> { // List<CursantNotaCursDTO>
        checkAuth.checkUserId(profesorId) // verifica daca ID-ul dat este cel al utilizatorului curent

        // obtine datele cursului si ale profesorului din BD
        val curs = findCursById(cursId, cursRepository)
        val profesor = findProfesorById(profesorId, profesorRepository)

        verificaDacaProfesorulPredaCursul(profesor, curs)

        return converter.listConverter(curs.listaCursanti, PersoanaDTO::class.java) // returneaza lista cursurilor
        //return profesorRepository.findCursantiNoteCurs(profesorId, cursId);
    }

    // verifica daca profesorul dat preda cursul dat
    private fun verificaDacaProfesorulPredaCursul(profesor: Profesor, curs: Curs) {
        if (!profesor.listaCursuri.contains(curs)) { // daca in lista nu exista cursul
            throw NegasitException("Nu predai acest curs")
        }
    }

    // actualizeaza nota unui cursant la un anumit curs la care presa profesorul si returneaza lista notelor cursantului
    override fun updateNota(profesorId: Long, cursantId: Long, cursId: Int, valoareNoua: Int): List<NotaDTO> {
        checkAuth.checkUserId(profesorId) // verifica daca ID-ul dat este cel al utilizatorului curent

        // obtine datele profesorului si ale cursului
        val profesor = findProfesorById(profesorId, profesorRepository)
        val curs = findCursById(cursId, cursRepository)

        verificaDacaProfesorulPredaCursul(profesor, curs)

        val cursant = findCursantById(cursantId, cursantRepository) // obtine datele studentului

        val nota: Nota = cursant.listaNote.stream() // cauta nota de la curs in lista lista notelor cursatului
            .filter { x -> x.cursId!! == curs }
            .findFirst() // daca s-a gasit, returneaza primul rezultat
            .orElseThrow { NegasitException("Cursantul nu s-a inscris la acest curs") }

        if (valoareNoua < 1 || valoareNoua > 10) { // verifica valoarea data
            throw ValoareNepermisaException("Doar valori intre 1 si 10")
        }

        nota.valoare = valoareNoua // seteaza noua valoare
        return converter.converter(cursantRepository.save(cursant), CursantDTO::class.java).listaNote // actualizeaza cursantul si returneaza lista notelor cursantului
    }

    // actualizeaza datele profesorului cu datele oferite si le returneaza
    override fun updateProfesor(id: Long, persoanaUpdateDto: PersoanaUpdateDTO): PersoanaDTO {
        checkAuth.checkUserId(id) // verifica daca ID-ul dat este cel al utilizatorului curent

        val profesor = findProfesorById(id, profesorRepository) // obtine datele profesorului din BD

        if (persoanaUpdateDto.parola != null) {
            persoanaUpdateDto.parola = encoder.encode(persoanaUpdateDto.parola) // cripteaza noua parola
        }

        val profesorActualizat: Profesor = converter.updateConverter(persoanaUpdateDto, profesor) // memoreaza datele actualizate

        return converter.converter(profesorRepository.save(profesorActualizat), PersoanaDTO::class.java) // actualizeaza datele profesorului in BD si le returneaza
    }

    // returneaza lista cursurilor la care preda profesorul ID
    override fun getCursuriProfesor(id: Long): Set<CursDTO> {
        checkAuth.checkUserId(id) // verifica daca ID-ul dat este cel al utilizatorului curent

        val profesor = findProfesorById(id, profesorRepository) // obtine datele profesorului
        return converter.converter(profesor, ProfesorDTO::class.java).listaCursuri // returneaza lista cursurilor la care preda
    }
}