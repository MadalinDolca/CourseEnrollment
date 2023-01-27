package com.madalin.proiectcursuri.controller

import com.madalin.proiectcursuri.dto.*
import com.madalin.proiectcursuri.service.IProfesorService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("profesor")
class ProfesorController(var profesorService: IProfesorService) {
    @GetMapping("check")
    fun check() {
    }

    // obtine datele profesorului
    @GetMapping("date/{email}")
    fun getDateProfesor(@PathVariable("email") email: String): ProfesorDTO {
        return profesorService.getDateProfesor(email)
    }

    // obtine cursurile predate
    @GetMapping("{id}/cursuri")
    fun getCursuriProfesor(@PathVariable("id") id: Long): Set<CursDTO?>? {
        return profesorService.getCursuriProfesor(id)
    }

    // obtine cursanti si note per curs
    @GetMapping("cursanti")
    fun getCursantiByCurs(
        @RequestParam(value = "profesorid") profesorid: Long,
        @RequestParam(value = "cursid") cursid: Int
    ): List<PersoanaDTO?>? { //List<CursantNotaCursDTO>
        return profesorService.getCursantiByCurs(profesorid, cursid)
    }

    // actualizeaza date personale
    @PatchMapping("{id}")
    fun updateProfesor(
        @PathVariable("id") id: Long,
        @RequestBody personUpdateDto: @Valid PersoanaUpdateDTO
    ): PersoanaDTO? {
        return profesorService.updateProfesor(id, personUpdateDto)
    }

    // actualizare nota
    @PatchMapping("note")
    fun updateNota(
        @RequestParam(value = "profesorid") profesorid: Long,
        @RequestParam(value = "cursantid") cursantid: Long,
        @RequestParam(value = "cursid") cursid: Int,
        @RequestBody valoareNoua: @Valid Int
    ): List<NotaDTO?>? {
        return profesorService.updateNota(profesorid, cursantid, cursid, valoareNoua)
    }
}