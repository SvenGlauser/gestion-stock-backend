package ch.glauser.gestionstock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GestionStockApplication {
    static void main(String... args) {
        SpringApplication.run(GestionStockApplication.class, args);
    }
}
