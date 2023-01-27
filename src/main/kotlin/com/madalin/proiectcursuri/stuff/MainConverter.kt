package com.madalin.proiectcursuri.stuff

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

// conversie de la Entity la DTO si invers
@Component // permite Spring-ului sa detecteze automat bean-urile personalizate
class MainConverter(final val modelMapper: ModelMapper) : IMainConverter {
    init {
        modelMapper.configuration.isSkipNullEnabled = true
    }

    override fun getMapper() = this.modelMapper
}