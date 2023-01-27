package com.madalin.proiectcursuri.service

import com.madalin.proiectcursuri.dto.*
import com.madalin.proiectcursuri.exception.ConflictException
import com.madalin.proiectcursuri.exception.NegasitException
import com.madalin.proiectcursuri.persistence.model.*
import com.madalin.proiectcursuri.persistence.repository.*
import com.madalin.proiectcursuri.service.UtilitarFindById.findAdminById
import com.madalin.proiectcursuri.service.UtilitarFindById.findCursById
import com.madalin.proiectcursuri.service.UtilitarFindById.findCursantById
import com.madalin.proiectcursuri.service.UtilitarFindById.findPersoanaById
import com.madalin.proiectcursuri.service.UtilitarFindById.findProfesorById
import com.madalin.proiectcursuri.service.UtilitarFindById.findRolById
import com.madalin.proiectcursuri.stuff.IMainConverter
import com.madalin.proiectcursuri.stuff.RolEnum
import com.madalin.proiectcursuri.stuff.TabelEnum
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Stream

// serviciu pentru manipularea datelor de catre un administrator
@Service // indica bean-ului faptul ca detine logica de afaceri
class AdminService(
    private val persoanaRepository: IPersoanaRepository,
    private val cursantRepository: ICursantRepository,
    private val profesorRepository: IProfesorRepository,
    private val adminRepository: IAdminRepository,
    private val cursRepository: ICursRepository,
    private val notaRepository: INotaRepository,
    private val rolRepository: IRolRepository,
    private val converter: IMainConverter,
    private val encoder: PasswordEncoder
) : IAdminService {

    // returneaza o lista cu toate rezultatele din tabelul specificat sortate in functie de coloana
    override fun getAllPersoane(tabel: String, coloana: String): List<PersoanaDTO> {
        return when (tabel.lowercase()) { // cauta si returneaza persoanele din tabelul dat
            TabelEnum.PERSOANA.numeTabel -> findAllPersoane(coloana)
            TabelEnum.CURSANT.numeTabel -> findAllCursanti(coloana)
            TabelEnum.PROFESOR.numeTabel -> findAllProfesori(coloana)
            TabelEnum.ADMIN.numeTabel -> findAllAdmini(coloana)
            else -> throw NegasitException("Tabel negasit")
        }
    }

    // returneaza o lista cu toate persoanele din tabel sortata in functie de coloana
    private fun findAllPersoane(coloana: String): List<PersoanaDTO> {
        // cauta persoanele si construieste lista sortata
        val persoane: List<Persoana> = persoanaRepository.findAll(Sort.by(coloana))

        return converter.listConverter(persoane, PersoanaDTO::class.java) // returneaza lista
    }

    // returneaza o lista cu toti cursantii din tabel sortata in functie de coloana
    private fun findAllCursanti(camp: String): List<PersoanaDTO> {
        // cauta cursantii si construieste lista sortata
        val cursanti: List<Cursant> = cursantRepository.findAll(Sort.by(camp))

        return converter.listConverter(cursanti, PersoanaDTO::class.java) // returneaza lista
    }

    // returneaza o lista cu toti profesorii din tabel sortata in functie de coloana
    private fun findAllProfesori(camp: String): List<PersoanaDTO> {
        // cauta profesorii si construieste lista sortata
        val profesori: List<Profesor> = profesorRepository.findAll(Sort.by(camp))

        return converter.listConverter(profesori, PersoanaDTO::class.java) // returneaza lista
    }

    // returneaza o lista cu toti administratorii din tabel sortata in functie de coloana
    private fun findAllAdmini(camp: String): List<PersoanaDTO> {
        // cauta administratorii si construieste lista sortata
        val admin: List<Admin> = adminRepository.findAll(Sort.by(camp))

        return converter.listConverter(admin, PersoanaDTO::class.java) // returneaza lista
    }

    // returneaza persoana care are ID-ul dat in tabelul specificat
    override fun getPersoanaById(tabel: String, id: Long): PersoanaDTO {
        return when (tabel.lowercase()) {
            TabelEnum.PERSOANA.numeTabel -> converter.converter(findPersoanaById(id, persoanaRepository), PersoanaDTO::class.java)
            TabelEnum.CURSANT.numeTabel -> converter.converter(findCursantById(id, cursantRepository), CursantDTO::class.java)
            TabelEnum.PROFESOR.numeTabel -> converter.converter(findProfesorById(id, profesorRepository), ProfesorDTO::class.java)
            TabelEnum.ADMIN.numeTabel -> converter.converter(findAdminById(id, adminRepository), AdminDTO::class.java)
            else -> throw NegasitException("Tabel negasit")
        }
    }

    // returneaza o lista cu toate persoanele din fiecare tabel care au un email similar cu cel dat
    override fun getPersoaneByEmail(email: String): List<PersoanaDTO> {
        // cauta in cele trei tabele persoanele care au emailul specificat si le memoreaza in liste
        val cursanti = cursantRepository.findLikeEmail(email)
        val profesori = profesorRepository.findLikeEmail(email)
        val admini = adminRepository.findLikeEmail(email)

        val persoane = mutableListOf<Persoana>()
        Stream.of(cursanti, profesori, admini).forEach(persoane::addAll) // adauga cele trei liste intr-una

        return converter.listConverter(persoane, PersoanaDTO::class.java) // returenaza noua lista
    }

    // returneaza o lista cu toate persoanele din fiecare tabel care au rolul dat
    override fun getPersoaneByRole(id: Int): List<PersoanaDTO> {
        val rol: Rol = findRolById(id, rolRepository) // obtine rolul din BD
        val rolId = rol.id

        // cauta in cele trei tabele persoanele care au rolul specificat si le memoreaza in liste
        val students = cursantRepository.findByRolId(rolId)
        val teachers = profesorRepository.findByRolId(rolId)
        val staff = adminRepository.findByRolId(rolId)

        val persoane = mutableListOf<Persoana>()
        Stream.of(students, teachers, staff).forEach(persoane::addAll) // adauga cele trei liste intr-una

        return converter.listConverter(persoane, PersoanaDTO::class.java) // returenaza noua lista
    }

    // returneaza o lista cu persoanele incluse intr-un curs in functie de tabelul dat
    override fun getPersoaneByCurs(tabel: String, id: Int): List<PersoanaDTO> {
        val curs = findCursById(id, cursRepository)

        return when (tabel.lowercase()) {
            TabelEnum.CURSANT.numeTabel -> converter.listConverter(curs.listaCursanti, PersoanaDTO::class.java)
            TabelEnum.PROFESOR.numeTabel -> converter.listConverter(curs.listaProfesori, PersoanaDTO::class.java)
            else -> throw NegasitException("Tabel negasit")
        }
    }

    // returneaza lista cursurilor la care activeaza persoana din tabelul specificat
    override fun getCursuriPersoana(tabel: String, id: Long): List<CursDTO> {
        return when (tabel.lowercase()) {
            TabelEnum.CURSANT.numeTabel -> {
                val cursant = findCursantById(id, cursantRepository) // cauta cursantul in functie de ID
                converter.listConverter(cursant.listaCursuri.stream().toList(), CursDTO::class.java) // returneaza lista cursurilor la care activeaza
            }

            TabelEnum.PROFESOR.numeTabel -> {
                val profesor = findProfesorById(id, profesorRepository) // cauta profesorul in functie de ID
                converter.listConverter(profesor.listaCursuri.stream().toList(), CursDTO::class.java) // returneaza lista cursurilor la care activeaza
            }

            else -> throw NegasitException("Tabel negasit")
        }
    }

    // returneaza o lista cu toate cursurile din baza de date
    override fun getAllCursuri(): List<CursDTO> {
        return converter.listConverter(cursRepository.findAll(), CursDTO::class.java)
    }

    // returneaza cursul care are ID-ul specificat in baza de date
    override fun getCursById(id: Int): CursDTO {
        val curs = findCursById(id, cursRepository)
        return converter.converter(curs, CursDTO::class.java)
    }

    // returneaza o lista cu toate rolurile din baza de date
    override fun getAllRoluri(): List<RolDTO> {
        return converter.listConverter(rolRepository.findAll(), RolDTO::class.java)
    }

    // adauga un nou utilizator in sistem
    override fun addPersoana(persoanaDto: PersoanaDTO): PersoanaDTO {
        // verifica daca adresa de email specificata este deja inregistrata in baza de date
        persoanaRepository.findByEmail(persoanaDto.email).ifPresent { x: Persoana? -> throw ConflictException("Email-ul exista deja") }

        // verifica daca rolul specificat exista in baza de date si-l memoreaza in caz afirmativ
        val rol = rolRepository.findById(persoanaDto.rolId!!).orElseThrow { NegasitException("Rol negasit") }

        persoanaDto.parola = encoder.encode(persoanaDto.parola) // cripteaza parola

        return when (rol.id) { // salveaza utilizatorul in tabel conform rolului acestuia
            RolEnum.CURSANT.id -> saveCursant(persoanaDto)
            RolEnum.PROFESOR.id -> saveProfesor(persoanaDto)
            else -> saveAdmin(persoanaDto)
        }
    }

    // adauga un cursant in baza de date
    private fun saveCursant(perdoanaDto: PersoanaDTO): CursantDTO {
        val cursant = converter.converter(perdoanaDto, Cursant::class.java) // memoreaza cursantul
        //cursant.incCursantId() // incrementeaza ID-ul static al cursantilor

        return converter.converter(cursantRepository.save(cursant), CursantDTO::class.java) // adauga cursantul in BD si returneaza obiectul acestuia
    }

    // adauga un profesor in baza de date
    private fun saveProfesor(persoanaDto: PersoanaDTO): ProfesorDTO {
        val profesor = converter.converter(persoanaDto, Profesor::class.java) // memoreaza profesorul
        //profesor.incProfesorId() // incrementeaza ID-ul static al profesorilor

        return converter.converter(profesorRepository.save(profesor), ProfesorDTO::class.java) // adauga profesorul in BD si returneaza obiectul acestuia
    }

    // adauga un administrator in baza de date
    private fun saveAdmin(persoanaDto: PersoanaDTO): AdminDTO {
        val admin = converter.converter(persoanaDto, Admin::class.java) // memoreaza administratorul
        //admin.incAdminId() // incrementeaza ID-ul static al administratorilor

        return converter.converter(adminRepository.save(admin), AdminDTO::class.java) // adauga administratorul in BD si returneaza obiectul acestuia
    }

    // adauga cursul dat in baza de date
    override fun addCurs(cursDto: CursDTO): CursDTO {
        // verifica daca cursul cu numele dat exista deja
        cursRepository.findByNumeCurs(cursDto.numeCurs).ifPresent { x -> throw ConflictException("Cursul exista deja") }

        // adauga cursul in baza de date  si-l memoreaza
        val subject = cursRepository.save(converter.converter(cursDto, Curs::class.java))

        return converter.converter(subject, CursDTO::class.java) // returneaza cursul dat
    }

    // actualizeaza parola persoanei cu ID-ul dat
    override fun updatePersoana(id: Long, updateDto: PersoanaUpdateDTO): PersoanaDTO {
        val persoana = findPersoanaById(id, persoanaRepository) // cauta persoana cu ID-ul dat
        val parolaNoua = updateDto.parola // memoreaza parola persoanei

        if (parolaNoua != null) {
            updateDto.parola = encoder.encode(parolaNoua) // memoreaza parola criptata
        }

        val persoanaActualizata: Persoana = converter.updateConverter(updateDto, persoana) // creeaza obiectul persoanei actualizate

        // adauga persoana actualizata in baza de date si returneaza obiectul acesteia
        return converter.converter(persoanaRepository.save(persoanaActualizata), PersoanaDTO::class.java)
    }

    // inscrie persoana ID din tabelul dat la cursul specificat
    override fun joinPersoanaLaCurs(tabel: String, id: Long, cursID: Int): List<PersoanaDTO> {
        val curs = findCursById(cursID, cursRepository) // cauta cursul cu ID-ul dat si-l memoreaza

        return when (tabel.lowercase()) { // adauga persoana din tabelul dat la curs
            TabelEnum.CURSANT.numeTabel -> joinCursant(id, curs)
            TabelEnum.PROFESOR.numeTabel -> joinProfesor(id, curs)
            else -> throw NegasitException("Tabel negasit")
        }
    }

    // inscrie cursantul dat la cursul specificat si returneaza lista cursantilor inscrisi la curs
    private fun joinCursant(id: Long, curs: Curs): List<PersoanaDTO> {
        val cursant = findCursantById(id, cursantRepository) // obtine cursantul cu ID-ul dat

        if (!cursant.adaugaCurs(curs)) { // verifica daca acest cursant este deja inscris la cursul respectiv si-l inscrie daca este posibil
            throw ConflictException("Cursantul este deja inscris la acest curs")
        }

        notaRepository.save(Nota(cursant, curs)) // adauga o inregistrare in tabelul notelor pentru cursantul curent
        cursantRepository.save(cursant) // actualizeaza cursantul in BD

        return converter.listConverter(curs.listaCursanti, PersoanaDTO::class.java) // returneaza lista cursantilor inscrisi la curs
    }

    // atribuie profesorul dat la cursul specificat si returneaza lista profesorilor atribuiti cursului
    private fun joinProfesor(id: Long, subject: Curs): List<PersoanaDTO> {
        val profesor = findProfesorById(id, profesorRepository) // obtine profesorul cu ID-ul dat

        if (!profesor.adaugaCurs(subject)) { // verifica daca acest profesor este deja atribuit cursului respectiv si-l atribuie daca este posibil
            throw ConflictException("Profesorul preda deja acest curs")
        }

        profesorRepository.save(profesor) // actualizeaza profesorul in BD
        return converter.listConverter(subject.listaProfesori, PersoanaDTO::class.java) // returneaza lista profesorilor atribuiti cursului
    }

    // inlatura persoana ID din tabelul dat la cursul specificat
    override fun unjoinPersoanaDeLaCurs(tabel: String, id: Long, cursID: Int): List<PersoanaDTO> {
        val curs = findCursById(cursID, cursRepository) // obtine cursul ID din bd

        return when (tabel.lowercase()) { // inlatura persoana din tabelul dat
            TabelEnum.CURSANT.numeTabel -> unjoinCursant(id, curs)
            TabelEnum.PROFESOR.numeTabel -> unjoinProfesor(id, curs)
            else -> throw NegasitException("Tabel negasit")
        }
    }

    // inlatura un cursant de la un anumit curs si returneaza lista cursantilor inscrisi la curs
    private fun unjoinCursant(id: Long, curs: Curs): List<PersoanaDTO> {
        val cursant = findCursantById(id, cursantRepository) // obtine cursantul ID din bd

        // obtine cursantul inscris la curs si-l elimina din bd
        notaRepository.delete(notaRepository.findByCursantAndCurs(cursant.id!!, curs.id!!)
            .orElseThrow { NegasitException("Nota negasita") })

        if (!cursant.stergeCurs(curs)) { // inlatura cursantul de la curs in cazul in care este inscris
            throw ConflictException("Cursantul nu este inscris la acest curs")
        }

        cursantRepository.save(cursant) // actualizeaza cursantul
        return converter.listConverter(curs.listaCursanti, PersoanaDTO::class.java) // returneaza lista cursantilor inscrisi la curs
    }

    // inlatura un profesor de la un anumit curs si returneaza lista profesorilor atribuiti la curs
    private fun unjoinProfesor(id: Long, curs: Curs): List<PersoanaDTO> {
        val profesor = findProfesorById(id, profesorRepository)

        if (!profesor.stergeCurs(curs)) { // inlatura profesorul de la curs in cazul in care este atribuit
            throw ConflictException("Profesorul nu preda acest curs")
        }

        profesorRepository.save(profesor) // actualizeaza profesorul
        return converter.listConverter(curs.listaProfesori, PersoanaDTO::class.java) // returneaza lista profesorilor atribuiti la curs
    }

    // sterge persoana data din baza de date
    override fun deletePersoana(id: Long) {
        this.persoanaRepository.delete(findPersoanaById(id, this.persoanaRepository))
    }

    // sterge cursul dat in baza de date
    override fun deleteCurs(id: Int) {
        findCursById(id, this.cursRepository)
        this.cursRepository.deleteFromCursanti(id) // sterge din lista cursantilor
        this.cursRepository.deleteFromProfesori(id) // sterge din lista profesorilor
        this.cursRepository.deleteCurs(id) // sterge cursul
    }
}
