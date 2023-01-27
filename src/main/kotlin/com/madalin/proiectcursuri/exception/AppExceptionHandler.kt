package com.madalin.proiectcursuri.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.function.Consumer


@ControllerAdvice // permite utilizarea exacta a aceleasi tehnici de gestionare a exceptiilor in intreaga aplicatie, nu doar unui controller individual
class AppExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus, // HttpStatus
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors: MutableMap<String, String?> = HashMap()

        exception.bindingResult.allErrors.forEach(Consumer { error ->
            val fieldName = (error as FieldError).field
            val message = error.getDefaultMessage()

            errors.put(fieldName, message)
        })

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [NegasitException::class])
    fun notFoundHandler(ex: RuntimeException, request: WebRequest?): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.NOT_FOUND, request!!)
    }

    @ExceptionHandler(value = [ConflictException::class])
    fun conflictHandler(ex: RuntimeException, request: WebRequest?): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.CONFLICT, request!!)
    }

    @ExceptionHandler(value = [ValoareNepermisaException::class])
    fun notAllowedHandler(ex: RuntimeException, request: WebRequest?): ResponseEntity<Any?>? {
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.BAD_REQUEST, request!!)
    }
}

class NegasitException(mesaj: String) : RuntimeException(mesaj)
class ConflictException(mesaj: String) : RuntimeException(mesaj)
class ValoareNepermisaException(mesaj: String) : RuntimeException(mesaj)