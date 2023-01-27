package com.madalin.proiectcursuri.controller

import com.madalin.proiectcursuri.dto.*
import com.madalin.proiectcursuri.service.ICursantService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("cursant")
class CursantController(var cursantService: ICursantService) {
    @GetMapping("check")
    fun check() {
    }

    // obtine datele cursantului
    @GetMapping("date/{email}")
    fun getDateCursant(@PathVariable("email") email: String): CursantDTO {
        return cursantService.getDateCursant(email)
    }

    // inregistrare
    @PostMapping
    fun signUp(@RequestBody cursantDto: @Valid CursantDTO): CursantDTO {
        return cursantService.signUp(cursantDto)
    }

    // obtine lista cursuri
    @GetMapping("cursuri")
    fun getAllCursuri(): List<CursDTO>? {
        return cursantService.getAllCursuri()
    }

    // obtine notele obtinute
    @GetMapping("{id}/note")
    fun getNoteCursant(@PathVariable("id") id: Long): List<NotaDTO>? {
        return cursantService.getNoteCursant(id)
    }

    // obtine cursurile la care s-a inscris
    @GetMapping("{id}/cursuri")
    fun getCursuriCursant(@PathVariable("id") id: Long): Set<CursDTO>? {
        return cursantService.getCursuriCursant(id)
    }

    // actualizare date personale
    @PatchMapping("{id}")
    fun updateCursant(
        @PathVariable("id") id: Long,
        @RequestBody personUpdateDto: @Valid PersoanaUpdateDTO
    ): PersoanaDTO {
        return cursantService.updateCursant(id, personUpdateDto)
    }

    // inscriere curs
    @PatchMapping("join")
    fun joinCurs(
        @RequestParam(value = "cursantid") cursantid: Long,
        @RequestParam(value = "cursid") cursid: Int
    ): Set<CursDTO>? {
        return cursantService.joinCurs(cursantid, cursid)
    }

    // abandonare curs
    @PatchMapping("unjoin")
    fun unjoinCurs(
        @RequestParam(value = "cursantid") cursantid: Long,
        @RequestParam(value = "cursid") cursid: Int
    ): Set<CursDTO>? {
        return cursantService.unjoinCurs(cursantid, cursid)
    }
}