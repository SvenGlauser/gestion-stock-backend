package ch.glauser.gestionstock.beans;

import ch.glauser.gestionstock.categorie.repository.CategorieRepository;
import ch.glauser.gestionstock.categorie.service.CategorieService;
import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeansGenerator {
    @Bean
    public CategorieService categorieService(CategorieRepository categorieRepository) {
        return new CategorieServiceImpl(categorieRepository);
    }
}
