package com.viniciusgomes.cursomc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viniciusgomes.cursomc.domain.PagamentoComBoleto;
import com.viniciusgomes.cursomc.domain.PagamentoComCartao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

//As classes de configuração fazer uma configuração inicial do sistema antes dele ser inicializado
@Configuration
public class JacksonConfig {
    // https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare

    // Os métodos das classes de configuração devem ser marcados com a anotação @Bean
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        // A operação abaixo é padrão para registro de classes no JSON
        // A única mudança é a inclusão das classes a serem registradas
        // que vão no método registerSubtypes()
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
            public void configure(ObjectMapper objectMapper) {
                objectMapper.registerSubtypes(PagamentoComCartao.class);
                objectMapper.registerSubtypes(PagamentoComBoleto.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}