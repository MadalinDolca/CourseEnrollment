package com.madalin.proiectcursuri.stuff

import com.madalin.proiectcursuri.persistence.model.*
import com.madalin.proiectcursuri.persistence.repository.*
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

//@Component // permite Spring-ului sa detecteze automat bean-urile personalizate
class IncarcaDate(
    private val cursantRepository: ICursantRepository,
    private val profesorRepository: IProfesorRepository,
    private val adminRepository: IAdminRepository,
    private val rolRepository: IRolRepository,
    private val cursRepository: ICursRepository,
    private val notaRepository: INotaRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        // roluri
        val cursantRol = Rol("CURSANT")
        val profesorRol = Rol("PROFESOR")
        val adminRol = Rol("ADMIN")
        val roluri = listOf(cursantRol, profesorRol, adminRol)

        // cursuri
        val contabilitate = Curs("Contabilitate si Gestiunea Afacerilor")
        val meditatie = Curs("Arta Meditatiei")
        val fotografie = Curs("Fotografie")
        val inteligentaArtificiala = Curs("Inteligenta Artificiala")
        val arteVizuale = Curs("Arte Vizuale")
        val cursuri = mutableSetOf(contabilitate, meditatie)
        val cursuriRamase = listOf(fotografie, inteligentaArtificiala, arteVizuale)

        // persoane
        val cursant = Cursant(1L, cursuri, "Ionut", "Popescu", "ipopescu@mail.com", passwordEncoder.encode("ipopescu"), cursantRol)
        val profesor = Profesor(1L, cursuri, "Bogdan", "Dascalescu", "bdascalescu@mail.com", passwordEncoder.encode("bdascalescu"), profesorRol)
        val admin = Admin(1L, "Root", "XYZ", "root@mail.com", passwordEncoder.encode("root"), adminRol)

        // note
        val notaContabilitate = Nota(8, cursant, contabilitate)
        val notaMeditatie = Nota(10, cursant, meditatie)

        // salveaza toate datele in baza de date
        rolRepository.saveAll(roluri)
        cursRepository.saveAll(cursuri)
        cursRepository.saveAll(cursuriRamase)
        cursantRepository.save(cursant)
        profesorRepository.save(profesor)
        adminRepository.save(admin)
        notaRepository.save(notaContabilitate)
        notaRepository.save(notaMeditatie)
    }

    /*override fun run(args: ApplicationArguments) {
        // roluri
        val cursantRol = Rol(numeRol = "CURSANT")
        val profesorRol = Rol(numeRol = "PROFESOR")
        val adminRol = Rol(numeRol = "ADMIN")
        val roluri = listOf(cursantRol, profesorRol, adminRol)

        // cursuri
        val contabilitate = Curs(numeCurs = "Contabilitate si Gestiunea Afacerilor")
        val meditatie = Curs(numeCurs = "Arta Meditatiei")
        val fotografie = Curs(numeCurs = "Fotografie")
        val inteligentaArtificiala = Curs(numeCurs = "Inteligenta Artificiala")
        val arteVizuale = Curs(numeCurs = "Arte Vizuale")
        val cursuri = mutableSetOf(contabilitate, meditatie)
        val cursuriRamase = listOf(fotografie, inteligentaArtificiala, arteVizuale)

        // persoane
        val cursant = Cursant(cursantId = 1L, listaCursuri = cursuri).apply {
            prenume = "Ionut"
            nume = "Popescu"
            email = "ipopescu@mail.com"
            parola = passwordEncoder.encode("ipopescu")
            rolId = cursantRol
        }

        val profesor = Profesor(profesorId = 1L, listaCursuri = cursuri).apply {
            prenume = "Bogdan"
            nume = "Dascalescu"
            email = "bdascalescu@mail.com"
            parola = passwordEncoder.encode("bdascalescu")
            rolId = profesorRol
        }

        val admin = Admin(adminId = 1L).apply {
            prenume = "Root"
            nume = "XYZ"
            email = "root@mail.com"
            parola = passwordEncoder.encode("root")
            rolId = adminRol
        }

        // note
        val notaContabilitate = Nota(valoare = 8, cursantId = cursant, cursId = contabilitate)
        val notaMeditatie = Nota(valoare = 10, cursantId = cursant, cursId = meditatie)

        // salveaza toate datele in baza de date
        rolRepository.saveAll(roluri)
        cursRepository.saveAll(cursuri)
        cursRepository.saveAll(cursuriRamase)
        cursantRepository.save(cursant)
        profesorRepository.save(profesor)
        adminRepository.save(admin)
        notaRepository.save(notaContabilitate)
        notaRepository.save(notaMeditatie)
    }*/
}
