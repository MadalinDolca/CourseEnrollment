package com.madalin.proiectcursuri.security

import com.madalin.proiectcursuri.persistence.model.Persoana
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class PersoanaAuthDetails(private var persoana: Persoana) : UserDetails {
    // https://www.baeldung.com/spring-security-granted-authority-vs-role
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val autoritati = mutableListOf<GrantedAuthority>()
        val rol = "ROLE_${persoana.rolId.numeRol}"
        val autoritate: GrantedAuthority = SimpleGrantedAuthority(rol)

        autoritati.add(autoritate)

        return autoritati
    }

    override fun getPassword() = persoana.parola

    override fun getUsername() = persoana.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}