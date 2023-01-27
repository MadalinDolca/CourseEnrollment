package com.madalin.proiectcursuri.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.madalin.proiectcursuri.exception.NegasitException
import com.madalin.proiectcursuri.persistence.repository.IPersoanaRepository
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class JwtAuthorizationFilter(
    authenticationManager: AuthenticationManager?,
    private val personRepository: IPersoanaRepository
) : BasicAuthenticationFilter(authenticationManager) {

    // activat de ori de cate ori exista o cerere de autorizare
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        println("======== doFilterInternal")
        val header = request.getHeader(JwtProperties.HEADER) // citeste header-ul de autorizare (locul in care ar trebui sa fie JWT token)

        // daca header-ul nu contine BEARER sau este null, deleaga pentru implementarea Spring si iese din metoda
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            println("======== doFilterInternal if")
            chain.doFilter(request, response)

            println("======== doFilterInternal if dupa chain")
            return
        }

        // daca header-ul este prezent, obtine principalul din baza de date si efectueaza autorizatia
        val authentication = getAutentificareEmailParola(request)
        SecurityContextHolder.getContext().authentication = authentication
        println("======== doFilterInternal pe final")

        chain.doFilter(request, response) // continua executia filtrului
    }

    private fun getAutentificareEmailParola(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(JwtProperties.HEADER)

        if (token != null) {
            println("======== getUsernamePasswordAuthentication if")

            // analizeaza token-ul si-l valideaza
            val username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.toByteArray()))
                .build()
                .verify(token.replace(JwtProperties.TOKEN_PREFIX, ""))
                .subject

            // cauta in baza de date persoana/utilizatorul care are token subject-ul dat (username/email)
            // daca exista, obtine detaliile utilizatorului si creeaza un Spring Auth Token folosind username-ul (email-ul), parola si autoritatile (rolurile)
            if (username != null) {
                println("======== getUsernamePasswordAuthentication if if")

                val persoana = personRepository.findByEmail(username).orElseThrow { NegasitException("Email negasit") }
                val principal = PersoanaAuthDetails(persoana)

                return UsernamePasswordAuthenticationToken(username, null, principal.authorities)
            }

            return null
        }

        return null
    }
}
