package ch.glauser.gestionstock.contact.controller;

import ch.glauser.gestionstock.GestionStockApplication;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.contact.dto.ContactDto;
import ch.glauser.gestionstock.contact.model.Titre;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = GestionStockApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ContactControllerTest {

    @Autowired
    ContactController contactController;

    @Test
    void get() {
        ContactDto contact = new ContactDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");

        contact = contactController.create(contact).getBody();

        assertThat(contact).isNotNull();

        ContactDto contactDto = contactController.get(contact.getId()).getBody();
        assertThat(contactDto).isNotNull();
        assertThat(contactDto.getNom())
                .isNotNull()
                .isEqualTo("Nom");
        assertThat(contactDto.getPrenom())
                .isNotNull()
                .isEqualTo("Prénom");
        assertThat(contactDto.getTitre())
                .isNotNull()
                .isEqualTo(Titre.MONSIEUR.name());
    }

    @Test
    void create() {
        // Test validation bien mise en place
        ContactDto contact = new ContactDto();
        TestUtils.testValidation(3, () -> contactController.create(contact));

        // Test cas OK
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");
        assertDoesNotThrow(() -> contactController.create(contact));
    }

    @Test
    void search() {
        ContactDto contact = new ContactDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");
        assertDoesNotThrow(() -> contactController.create(contact));

        ContactDto contact2 = new ContactDto();
        contact2.setTitre(Titre.MONSIEUR.name());
        contact2.setNom("Nom");
        contact2.setPrenom("Prénom 2");
        assertDoesNotThrow(() -> contactController.create(contact2));

        ContactDto contact3 = new ContactDto();
        contact3.setTitre(Titre.MONSIEUR.name());
        contact3.setNom("Nom 2");
        contact3.setPrenom("Prénom 2");
        assertDoesNotThrow(() -> contactController.create(contact3));

        SearchRequest searchRequest = new SearchRequest();
        
        SearchResult<ContactDto> result = contactController.search(searchRequest).getBody();
        assertThat(result).isNotNull();
        assertThat(result.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        Filter nom = new Filter();
        nom.setValue("Nom");
        nom.setField("nom");
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setFilters(List.of(nom));
        SearchResult<ContactDto> result2 = contactController.search(searchRequest2).getBody();
        assertThat(result2).isNotNull();
        assertThat(result2.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void modify() {
        ContactDto contact = new ContactDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");

        contact = contactController.create(contact).getBody();

        assertThat(contact).isNotNull();

        ContactDto contactDto = contactController.get(contact.getId()).getBody();
        assertThat(contactDto).isNotNull();
        assertThat(contactDto.getNom())
                .isNotNull()
                .isEqualTo("Nom");
        assertThat(contactDto.getPrenom())
                .isNotNull()
                .isEqualTo("Prénom");
        assertThat(contactDto.getTitre())
                .isNotNull()
                .isEqualTo(Titre.MONSIEUR.name());

        contact.setTitre(Titre.MADAME.name());
        contact.setNom("Nom 2");
        contact.setPrenom("Prénom 2");

        contactController.modify(contact).getBody();

        ContactDto contactDto2 = contactController.get(contact.getId()).getBody();
        assertThat(contactDto2).isNotNull();
        assertThat(contactDto2.getNom())
                .isNotNull()
                .isEqualTo("Nom 2");
        assertThat(contactDto2.getPrenom())
                .isNotNull()
                .isEqualTo("Prénom 2");
        assertThat(contactDto2.getTitre())
                .isNotNull()
                .isEqualTo(Titre.MADAME.name());
    }

    @Test
    void delete() {
        ContactDto contact = new ContactDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");

        contact = contactController.create(contact).getBody();

        assertThat(contact).isNotNull();

        contactController.delete(contact.getId());

        contact = contactController.get(contact.getId()).getBody();

        assertThat(contact).isNull();

        // Suppression inexistant
        TestUtils.testValidation(1, () -> contactController.delete(1000L));
    }
}