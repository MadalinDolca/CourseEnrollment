package com.madalin.proiectcursuri.stuff

import org.modelmapper.ModelMapper
import java.util.stream.Collectors

// conversie de la Entity la DTO si invers
interface IMainConverter {
    fun getMapper(): ModelMapper

    fun <T, D> converter(inClass: T, outClass: Class<D>): D {
        return getMapper().map(inClass, outClass)
    }

    fun <D, T> listConverter(entityList: List<T>, outCLass: Class<D>): List<D> {
        return entityList.stream()
            .map { entity: T -> getMapper().map(entity, outCLass) }
            .collect(Collectors.toList())
    }

    fun <T, D> updateConverter(update: T, result: D): D {
        getMapper().map(update, result)
        return result
    }
}