package ch.glauser.gestionstock.batch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "gestion-stock.batch")
public class BatchProperties {
    private String username;
    private String password;
}
