package com.devsu.cuenta.bancaria.config.scheduling;

import com.devsu.cuenta.bancaria.business.services.cuenta.LimiteDiarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulingConfiguration {

    private final LimiteDiarioService limiteDiarioService;

    public SchedulingConfiguration(LimiteDiarioService limiteDiarioService) {
        this.limiteDiarioService = limiteDiarioService;
    }

    @Bean
    public ScheduledTaskRegistrar taskRegistrar() {
        ScheduledTaskRegistrar registrar = new ScheduledTaskRegistrar();
        return registrar;
    }

    // Todos los dias se debe eliminar los datos de la tabla limite_diario, para reinicar el limite
    @Scheduled(cron = "0 0 12 * * *")
    public void scheduledTask() {
        limiteDiarioService.eliminarTodosLosRegistros();
    }
}
