package ch.glauser.gestionstock.configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
@EnableJpaAuditing
public class AuditorAwareConfiguration implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        if (principal instanceof UserDetails user) {
            return Optional.of(user.getUsername());
        }

        if (principal instanceof AnonymousAuthenticationToken anonymousAuthenticationToken) {
            return Optional.of(anonymousAuthenticationToken.getPrincipal().toString());
        }

        return Optional.of(principal.toString());
    }
}
