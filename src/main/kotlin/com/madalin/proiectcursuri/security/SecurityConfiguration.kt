package com.madalin.proiectcursuri.security

import com.madalin.proiectcursuri.persistence.repository.IPersoanaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration // ii spune containerului Spring ca exista un bean care trebuie tratat in runtime
@EnableWebSecurity
class SecurityConfiguration(
    private val persoanaService: PersoanaDetailsService,
    private val persoanaRepository: IPersoanaRepository
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
    override fun configure(http: HttpSecurity) {
        // elimina csrf si session state
        http.csrf().disable() // permite utilizarea altor metode decat GET (csrf util în autentificarea bazata pe formular, in JWT nu este util)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // nu este util in JWT, doar pe baza de formular

            .and() // adauga filtrele JWT (1. autentificare, 2. autorizare)
            .addFilter(JwtAuthenticationFilter(authenticationManager())) // conteaza ordinea filtrelor
            .addFilter(JwtAuthorizationFilter(authenticationManager(), persoanaRepository)) // configurare reguli acces

            .authorizeRequests()
            .antMatchers("/css/**").permitAll() // permite obtinerea resurselor fara autentificare
            .antMatchers("/").authenticated()
            .antMatchers("/inregistrare").permitAll() // permite accesarea "/inregistrare" fara autentificare
            .antMatchers(HttpMethod.POST, "/cursant").permitAll() // oricine isi poate crea cont de cursant
            .antMatchers("/cursant/**").hasRole("CURSANT")
            .antMatchers("/profesor/**").hasRole("PROFESOR")
            .antMatchers("/admin/**").hasRole("ADMIN")

            //.anyRequest().authenticated() // autorizeaza toate request-urile utilizatorilor autentificati

            .and()
            .formLogin { // personalizare formular login
                it.loginPage("/autentificare")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/") // eh
                    .permitAll()
            }
            .logout {
                it.logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/autentificare")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
            }
        //.httpBasic()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        daoAuthenticationProvider.setUserDetailsService(persoanaService)

        return daoAuthenticationProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder() // pentru criptarea parolelor
}
