package com.madalin.proiectcursuri.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.madalin.proiectcursuri.persistence.model.LoginViewModel
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.ArrayList

class JwtAuthenticationFilter(
    private val authenticationManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    // se activeaza atunci cand se face un POST request la /login
    // creeaza /login automat
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        return try {
            val credentiale = ObjectMapper().readValue(request.inputStream, LoginViewModel::class.java) // obtine credentialele si le mapeaza la LoginViewModel
            val authenticationToken = UsernamePasswordAuthenticationToken(credentiale.getUsername(), credentiale.parola, ArrayList()) // creaza login token

            authenticationManager.authenticate(authenticationToken) // RETURN: autentifica utilizatorul
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        val principal = authResult.principal as PersoanaAuthDetails // obtine principal
        val token = JWT.create() // creeaza JWT token
            .withSubject(principal.username)
            .withExpiresAt(Date(System.currentTimeMillis() + JwtProperties.TIMP_EXPIRARE)) // data expirarii = timp curent + timp dorit
            .sign(Algorithm.HMAC512(JwtProperties.SECRET.toByteArray()))

        response.addHeader(JwtProperties.HEADER, JwtProperties.TOKEN_PREFIX + token) // adauga token in response header
    }
}