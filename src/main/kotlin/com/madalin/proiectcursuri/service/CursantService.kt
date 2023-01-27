package com.madalin.proiectcursuri.service

import com.madalin.proiectcursuri.stuff.CheckAuth
import com.madalin.proiectcursuri.stuff.IMainConverter
import com.madalin.proiectcursuri.dto.*
import com.madalin.proiectcursuri.stuff.RolEnum
import com.madalin.proiectcursuri.exception.ConflictException
import com.madalin.proiectcursuri.exception.NegasitException
import com.madalin.proiectcursuri.service.UtilitarFindById.findCursById
import com.madalin.proiectcursuri.service.UtilitarFindById.findCursantById
import com.madalin.proiectcursuri.persistence.model.Cursant
import com.madalin.proiectcursuri.persistence.model.Nota
import com.madalin.proiectcursuri.persistence.repository.ICursRepository
import com.madalin.proiectcursuri.persistence.repository.ICursantRepository
import com.madalin.proiectcursuri.persistence.repository.INotaRepository
import com.madalin.proiectcursuri.persistence.repository.IRolRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

// serviciu pentru activitatea unui cursant
@Service // indica bean-ului faptul ca detine logica de afaceri
class CursantService(
    private var cursantRepository: ICursantRepository,
    private var cursRepository: ICursRepository,
    private var rolRepository: IRolRepository,
    private var notaRepository: INotaRepository,
    private var converter: IMainConverter,
    private var encoder: PasswordEncoder,
    private var checkAuth: CheckAuth
) : ICursantService {
    // obtine datele cursantului
    override fun getDateCursant(email: String): CursantDTO {
        val cursant = cursantRepository.findByEmail(email) // obtine datele din baza de date
        return converter.converter(cursant, CursantDTO::class.java) // returneaza datele cursantului
    }

    // obtine si returneaza o lista cu toate cursurile din baza de date
    override fun getAllCursuri(): List<CursDTO> {
        return converter.listConverter(cursRepository.findAll(), CursDTO::class.java)
    }

    // obtine notele cursantului curent
    override fun getNoteCursant(id: Long): List<NotaDTO> {
        checkAuth.checkUserId(id) // verifica daca ID-ul dat este cel al utilizatorului curent

        val cursant = findCursantById(id, cursantRepository) // obtine cursantul cu ID-ul dat
        return converter.converter(cursant, CursantDTO::class.java).listaNote // returneaza notele cursantului
    }

    // obtine lista cursurilor la care este inscris cursantul dat
    override fun getCursuriCursant(id: Long): Set<CursDTO> {
        checkAuth.checkUserId(id) // verifica daca ID-ul dat este cel al utilizatorului curent

        val cursant = findCursantById(id, cursantRepository) // obtine cursantul cu ID-ul dat din baza de date
        return converter.converter(cursant, CursantDTO::class.java).listaCursuri
    }

    // creeaza un nou cursant, il adauga in baza de date si i-l returneaza
    override fun signUp(cursantDTO: CursantDTO): CursantDTO {
        // verifica daca adresa de email specificata exista deja
        cursantRepository.findByEmail(cursantDTO.email!!).ifPresent { cursant: Cursant? -> throw ConflictException("Email-ul exista deja") }

        val cursant: Cursant = converter.converter(cursantDTO, Cursant::class.java) // obtine datele cursantului

        // atribuie rolul de cursant (daca exista) utilizatorului curent
        cursant.rolId = rolRepository.findById(RolEnum.CURSANT.id).orElseThrow { NegasitException("Rol negasit") }
        cursant.parola = encoder.encode(cursant.parola) // cripteaza parola
        //cursant.incCursantId() // incrementeaza numarul static de cursanti

        // adauga cursantul in bd si i-l returneaza
        return converter.converter(cursantRepository.save(cursant), CursantDTO::class.java)
    }

    // actualizeaza parola cursantului si il returneaza
    override fun updateCursant(id: Long, persoanaUpdateDto: PersoanaUpdateDTO): PersoanaDTO {
        checkAuth.checkUserId(id) // verifica daca ID-ul dat este cel al utilizatorului curent

        val cursant = findCursantById(id, cursantRepository) // obtine utilizatorul cu ID-ul dat

        if (persoanaUpdateDto.parola != null) {
            persoanaUpdateDto.parola = encoder.encode(persoanaUpdateDto.parola) // cripteaza noua parola
        }

        val cursantActualizat: Cursant = converter.updateConverter(persoanaUpdateDto, cursant) // obtine noul cursant

        return converter.converter(cursantRepository.save(cursantActualizat), PersoanaDTO::class.java) // actualizeaza cursantul in baza de date si il returneaza
    }

    // inscrie cursatul la cursul dat si returneaza lista cursurilor la care este inscris
    override fun joinCurs(cursantId: Long, cursId: Int): Set<CursDTO> {
        checkAuth.checkUserId(cursantId) // verifica daca ID-ul dat este cel al utilizatorului curent

        // obtine cursantul si cursul in functie de ID-uri din baza de date
        val cursant = findCursantById(cursantId, cursantRepository)
        val curs = findCursById(cursId, cursRepository)

        if (!cursant.adaugaCurs(curs)) { // adauga cursul in lista cursantului daca este posibil
            throw ConflictException("Te-ai inscris deja la acest curs")
        }

        notaRepository.save(Nota(cursant, curs)) // adauga o inregistrare in tabelul notelor pentru cursantul curent

        // actualizeaza cursantul in BD si returneaza lista cursurilor la care este inscris
        return converter.converter(cursantRepository.save(cursant), CursantDTO::class.java).listaCursuri
    }

    override fun unjoinCurs(cursantId: Long, cursId: Int): Set<CursDTO> {
        checkAuth.checkUserId(cursantId)

        // obtine cursantul si cursul in functie de ID-uri din baza de date
        val cursant = findCursantById(cursantId, cursantRepository)
        val curs = findCursById(cursId, cursRepository)

        // obtine inregistrarea notei cursantului din BD la cursul dat si o sterge
        notaRepository.delete(notaRepository.findByCursantAndCurs(cursant.id!!, curs.id).orElseThrow { NegasitException("Nota negasita") })

        if (!cursant.stergeCurs(curs)) { // inlatura cursul din lista cursantului daca acesta exista
            throw ConflictException("Nu te-ai alaturat acestui curs")
        }

        // actualizeaza cursantul si returneaza lista cursurilor la care acesta este inscris
        return converter.converter(cursantRepository.save(cursant), CursantDTO::class.java).listaCursuri
    }
}