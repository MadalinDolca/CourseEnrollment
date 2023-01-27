package com.madalin.proiectcursuri.controller

import com.madalin.proiectcursuri.service.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {
    @GetMapping("/inregistrare")
    fun inregistrare() = "auth/inregistrare"

    @GetMapping("/autentificare")
    fun autentificare() = "auth/autentificare"

    // ADMIN
    @GetMapping("/a")
    fun adminMain() = "admin/main"

    @GetMapping("/a/persoane") // persoanele din sistem
    fun adminPersoane() = "admin/persoane"

    @GetMapping("/a/cursuri") // cursurile din sistem
    fun adminCursuri() = "admin/cursuri"

    // PROFESOR
    @GetMapping("/p") // cursurile pe care le preda
    fun profesorMain() = "profesor/main"

    @GetMapping("/p/profil") // profil personal
    fun profesorProfil() = "profesor/profil"

    @GetMapping("/p/cursanti") // cursantii care participa la curs
    fun profesorCursanti() = "profesor/cursanti"

    // CURSANT
    @GetMapping("/c") // pagina cursurilor disponibile
    fun cursantMain() = "cursant/main"

    @GetMapping("/c/profil") // profil personal
    fun cursantProfil() = "cursant/profil"

    @GetMapping("/c/inscrieri") // cursurile la care este inscris
    fun cursantInscrieri() = "cursant/inscrieri"

    @GetMapping("/c/note") // notele obtinute
    fun cursantNote() = "cursant/note"

//    @PostMapping("/")
//    fun verificaRol(@RequestBody utilizator: @Valid Utilizator): String {
//        println(utilizator)
//        return if (!adminRepository.findByEmail(utilizator.email).isEmpty) "admin"
//        else if (!profesorRepository.findByEmail(utilizator.email).isEmpty) "profesor"
//        else if (!cursantRepository.findByEmail(utilizator.email).isEmpty) "cursant"
//        else "auth/autentificare"
//    }
}

//data class Utilizator(var email: String, var parola: String)
