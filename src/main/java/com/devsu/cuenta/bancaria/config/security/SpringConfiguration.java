package com.devsu.cuenta.bancaria.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringConfiguration implements WebMvcConfigurer {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui.html", "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**", "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().headers().frameOptions().disable().and().authorizeRequests()
                // Configurar el acceso libre a swagger ui
                .antMatchers(AUTH_WHITELIST).permitAll()
                // Configurar el acceso libre a la consola de h2
                .antMatchers("/h2-console/**").permitAll()
                // Configurar el acceso al api rest para obtener los datos de un usuarioS
                .antMatchers(HttpMethod.GET, "/usuarios/**").hasAuthority("ROLE_ADMINISTRADOR")
                // Configurar el acceso al api rest para obtener los datos de un clientes
                .antMatchers(HttpMethod.GET, "/clientes/**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_USUARIO")
                // Configurar el acceso al api rest para obtener los datos de una cuenta
                .antMatchers(HttpMethod.GET, "/cuentas/**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_USUARIO")
                // Configurar el acceso al api rest para obtener los datos de un movimiento
                .antMatchers(HttpMethod.GET, "/movimientos/**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_USUARIO")
                // Configurar el acceso al api rest para obtener los datos de un reporte
                .antMatchers(HttpMethod.GET, "/reportes/**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_USUARIO")
                // todas las dem[as deben estar autenticadas.
                .anyRequest().authenticated().and()
                .httpBasic().and().headers().referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.ORIGIN.ORIGIN);
        return http.build();
    }
}
