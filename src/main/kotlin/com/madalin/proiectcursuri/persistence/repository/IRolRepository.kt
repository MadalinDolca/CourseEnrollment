package com.madalin.proiectcursuri.persistence.repository

import com.madalin.proiectcursuri.persistence.model.Rol
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IRolRepository : JpaRepository<Rol, Int>