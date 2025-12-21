package ch.glauser.gestionstock.configuration;

import ch.glauser.gestionstock.security.SecurityHelper;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@EnableJpaAuditing
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityHelper.getLoggerUsername();
    }
}
