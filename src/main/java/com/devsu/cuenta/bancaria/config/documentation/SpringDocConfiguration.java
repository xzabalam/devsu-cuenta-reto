package com.devsu.cuenta.bancaria.config.documentation;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    private static final String GRUPO = "controladores";
    private static final String REGLA_USUARIOS = "/usuarios/**";
    private static final String REGLA_CLIENTES = "/clientes/**";
    private static final String REGLA_MOVIMIENTOS = "/movimientos/**";
    private static final String REGLA_REPORTES = "/reportes/**";

    @Bean
    public GroupedOpenApi getDocket() {
        return GroupedOpenApi.builder().group(GRUPO).pathsToMatch(REGLA_USUARIOS, REGLA_CLIENTES, REGLA_MOVIMIENTOS,
                REGLA_REPORTES).build();
    }
}
