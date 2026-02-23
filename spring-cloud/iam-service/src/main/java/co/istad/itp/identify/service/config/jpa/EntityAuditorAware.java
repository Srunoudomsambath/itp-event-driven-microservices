package co.istad.itp.identify.service.config.jpa;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;


// use for implement logic
public class EntityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("itp"); // inject security bean
    }
}
