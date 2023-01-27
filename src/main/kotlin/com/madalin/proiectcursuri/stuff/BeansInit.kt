package com.madalin.proiectcursuri.stuff

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration // ii spune containerului Spring ca exista un bean care trebuie tratate in runtime
class BeansInit {
    // conversia unui model in altul, permite modelelor separate sa ramana izolate
    @Bean
    fun modelMapper() = ModelMapper()
}