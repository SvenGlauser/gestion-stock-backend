package ch.glauser.gestionstock.utils;

import ch.glauser.gestionstock.security.SecurityRoles;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class TestSecurityConfiguration {

    public static final String TEST_ADMIN_USERNAME = "test-admin";
    public static final String TEST_ADMIN_PASSWORD = "test-password";

    @Bean
    @Primary
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username(TestSecurityConfiguration.TEST_ADMIN_USERNAME)
                .password(passwordEncoder.encode(TestSecurityConfiguration.TEST_ADMIN_PASSWORD))
                .authorities(Arrays
                        .stream(SecurityRoles.values())
                        .map(Enum::name)
                        .map(role -> "R_" + role)
                        .toArray(String[]::new))
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
