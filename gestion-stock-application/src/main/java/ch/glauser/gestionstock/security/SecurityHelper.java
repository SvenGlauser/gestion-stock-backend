package ch.glauser.gestionstock.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityHelper {
    /**
     * Lire le nom d'utilisateur de l'utilisateur connecté
     * @return Le nom d'utilisateur de l'utilisateur connecté, ou vide si non authentifié
     */
    public static Optional<String> getLoggerUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Authentification JWT avec Keycloak
        if (authentication instanceof JwtAuthenticationToken jwtToken) {
            return Optional.ofNullable(jwtToken.getName());
        }

        // Authentification avec UserDetails (ex: Basic Auth)
        if (authentication instanceof UserDetails user) {
            return Optional.of(user.getUsername());
        }

        // Authentification anonyme
        if (authentication instanceof AnonymousAuthenticationToken anonymousAuthenticationToken) {
            return Optional.of(anonymousAuthenticationToken.getPrincipal().toString());
        }

        // Authentification avec UsernamePasswordAuthenticationToken (Jobs Spring Batch)
        if (authentication instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
            Object potentialUser = usernamePasswordAuthenticationToken.getPrincipal();

            if (potentialUser instanceof User user) {
                return Optional.of(user.getUsername());
            }

            return Optional.of(potentialUser.toString());
        }

        // Pas d'authentification
        if (Objects.isNull(authentication)) {
            return Optional.empty();
        }

        // Autres types d'authentification
        return Optional.of(authentication.toString());
    }
}
