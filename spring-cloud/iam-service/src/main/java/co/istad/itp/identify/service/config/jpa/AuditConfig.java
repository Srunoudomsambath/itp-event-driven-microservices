package co.istad.itp.identify.service.config.jpa;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // to work
public class AuditConfig{

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new EntityAuditorAware();
    }
}
