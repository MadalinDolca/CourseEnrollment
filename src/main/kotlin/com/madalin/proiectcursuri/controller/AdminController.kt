package com.madalin.proiectcursuri.controller

import com.madalin.proiectcursuri.dto.CursDTO
import com.madalin.proiectcursuri.dto.PersoanaDTO
import com.madalin.proiectcursuri.dto.PersoanaUpdateDTO
import com.madalin.proiectcursuri.dto.RolDTO
import com.madalin.proiectcursuri.service.IAdminService
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

//@Controller // pentru GraphQL
@RestController
@RequestMapping("admin")
class AdminController(var adminService: IAdminService) {
    @GetMapping("check")
    fun check() {
    }

    ////////////////// PERSOANE //////////////////
    // obtine persoanele din tabelul "tabel" sortati in functie de "coloana"
    @GetMapping("{tabel}")
    //@QueryMapping
    fun getAllPersoane(
        @PathVariable("tabel") tabel: String,
        @RequestParam(value = "coloana") coloana: String
    ): List<PersoanaDTO>? {
        return adminService.getAllPersoane(tabel, coloana)
    }

    // adauga o persoana in BD si o returneaza
    @PostMapping("persoana")
    fun addPersoana(@RequestBody persoanaDTO: @Valid PersoanaDTO): PersoanaDTO {
        return adminService.addPersoana(persoanaDTO)
    }

    // sterge o persoana dupa ID
    @DeleteMapping("persoana/{id}")
    fun deletePersoana(@PathVariable("id") id: Long) {
        adminService.deletePersoana(id)
    }

    // actualizeaza datele persoanei ID
    @PatchMapping("persoana/{id}")
    fun updatePersoana(
        @PathVariable("id") id: Long,
        @RequestBody updateDto: @Valid PersoanaUpdateDTO
    ): PersoanaDTO {
        return adminService.updatePersoana(id, updateDto)
    }


    ////////////////// PERSOANE & CURSURI //////////////////
    // obtine cursurile persoanei "id" din tabelul "tabel"
    @GetMapping("{tabel}/id/{id}")
    fun getCursuriPersoana(
        @PathVariable("tabel") table: String,
        @PathVariable("id") id: Long
    ): List<CursDTO>? {
        return adminService.getCursuriPersoana(table, id)
    }

    // inscrie persoana din tabelul "tabel" de la cursul "cursId"
    @PatchMapping("join")
    fun joinPersoanaLaCurs(
        @RequestParam(value = "tabel") tabel: String,
        @RequestParam(value = "id") id: Long,
        @RequestParam(value = "cursid") cursid: Int
    ): List<PersoanaDTO>? {
        return adminService.joinPersoanaLaCurs(tabel, id, cursid)
    }

    // inlatura persoana din tabelul "tabel" de la cursul "cursId"
    @PatchMapping("unjoin")
    fun unjoinPersoanaDeLaCurs(
        @RequestParam(value = "tabel") tabel: String,
        @RequestParam(value = "id") id: Long,
        @RequestParam(value = "cursid") cursid: Int
    ): List<PersoanaDTO>? {
        return adminService.unjoinPersoanaDeLaCurs(tabel, id, cursid)
    }

    ////////////////// CURSURI //////////////////
    // obtine toate cursurile
    @GetMapping("cursuri")
    fun getAllCursuri(): List<CursDTO>? {
        return adminService.getAllCursuri()
    }

    // adauga un curs in sistem
    @PostMapping("cursuri")
    fun addCurs(@RequestBody cursDto: @Valid CursDTO): CursDTO {
        return adminService.addCurs(cursDto)
    }

    // sterge un curs dupa ID
    @DeleteMapping("cursuri/{id}")
    fun deleteCurs(@PathVariable("id") id: Int) {
        adminService.deleteCurs(id)
    }

    ////////////////// ROLURI //////////////////
    // returneaza o lista cu toate rolurile
    //@QueryMapping
    @GetMapping("roluri")
    fun getAllRoluri(): List<RolDTO>? {
        return adminService.getAllRoluri()
    }

    ////////////////// CAUTA //////////////////
    // obtine persoanele de la cursul "id" din tabelul "tabel"
    @GetMapping("cursuri/{id}/tabel/{tabel}")
    fun getPersoaneByCurs(
        @PathVariable("id") id: Int,
        @PathVariable("tabel") table: String
    ): List<PersoanaDTO>? {
        return adminService.getPersoaneByCurs(table, id)
    }

    // obtine persoana din tabelul "tabel" cu ID-ul "id"
    @GetMapping
    fun getPersoanaById(
        @RequestParam(value = "tabel") tabel: String,
        @RequestParam(value = "id") id: Long
    ): PersoanaDTO {
        return adminService.getPersoanaById(tabel, id)
    }

    // obtine persoane in functie de email
    @GetMapping("persoana/email")
    fun getPersoaneByEmail(@RequestBody email: @Valid String): List<PersoanaDTO>? {
        return adminService.getPersoaneByEmail(email)
    }

    // obtine persoane in functie de ID rol
    @GetMapping("persoana/rolid/{id}")
    fun getPersoaneByRol(@PathVariable("id") id: Int): List<PersoanaDTO>? {
        return adminService.getPersoaneByRole(id)
    }

    // obtine un curs dupa ID
    @GetMapping("cursuri/{id}")
    fun getCursById(@PathVariable("id") id: Int): CursDTO {
        return adminService.getCursById(id)
    }
}