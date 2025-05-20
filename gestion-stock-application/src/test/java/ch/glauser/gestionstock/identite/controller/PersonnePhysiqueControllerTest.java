package ch.glauser.gestionstock.identite.controller;

import ch.glauser.gestionstock.GestionStockApplication;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.validation.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.identite.dto.IdentiteLightDto;
import ch.glauser.gestionstock.identite.dto.PersonnePhysiqueDto;
import ch.glauser.gestionstock.identite.model.Titre;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = GestionStockApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PersonnePhysiqueControllerTest {

    @Autowired
    PersonnePhysiqueController personnePhysiqueController;

    @Autowired
    IdentiteController identiteController;

    @Test
    void get() {
        PersonnePhysiqueDto contact = new PersonnePhysiqueDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");

        contact = personnePhysiqueController.create(contact).getBody();

        assertThat(contact).isNotNull();

        PersonnePhysiqueDto personnePhysiqueDto = personnePhysiqueController.get(contact.getId()).getBody();
        assertThat(personnePhysiqueDto).isNotNull();
        assertThat(personnePhysiqueDto.getNom())
                .isNotNull()
                .isEqualTo("Nom");
        assertThat(personnePhysiqueDto.getPrenom())
                .isNotNull()
                .isEqualTo("Prénom");
        assertThat(personnePhysiqueDto.getTitre())
                .isNotNull()
                .isEqualTo(Titre.MONSIEUR.name());
    }

    @Test
    void create() {
        // Test validation bien mise en place
        PersonnePhysiqueDto contact = new PersonnePhysiqueDto();
        TestUtils.testValidation(3, () -> personnePhysiqueController.create(contact));

        // Test cas OK
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");
        assertDoesNotThrow(() -> personnePhysiqueController.create(contact));
    }

    @Test
    void search() {
        PersonnePhysiqueDto contact = new PersonnePhysiqueDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");
        assertDoesNotThrow(() -> personnePhysiqueController.create(contact));

        PersonnePhysiqueDto contact2 = new PersonnePhysiqueDto();
        contact2.setTitre(Titre.MONSIEUR.name());
        contact2.setNom("Nom");
        contact2.setPrenom("Prénom 2");
        assertDoesNotThrow(() -> personnePhysiqueController.create(contact2));

        PersonnePhysiqueDto contact3 = new PersonnePhysiqueDto();
        contact3.setTitre(Titre.MONSIEUR.name());
        contact3.setNom("Nom 2");
        contact3.setPrenom("Prénom 2");
        assertDoesNotThrow(() -> personnePhysiqueController.create(contact3));

        SearchRequest searchRequest = new SearchRequest();
        
        SearchResult<IdentiteLightDto> result = identiteController.search(searchRequest).getBody();
        assertThat(result).isNotNull();
        assertThat(result.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        Filter nom = new Filter();
        nom.setValue("Nom");
        nom.setField("nom");
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setCombinators(List.of(FilterCombinator.and(List.of(nom))));
        SearchResult<IdentiteLightDto> result2 = identiteController.search(searchRequest2).getBody();
        assertThat(result2).isNotNull();
        assertThat(result2.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void modify() {
        PersonnePhysiqueDto contact = new PersonnePhysiqueDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");

        contact = personnePhysiqueController.create(contact).getBody();

        assertThat(contact).isNotNull();

        PersonnePhysiqueDto personnePhysiqueDto = personnePhysiqueController.get(contact.getId()).getBody();
        assertThat(personnePhysiqueDto).isNotNull();
        assertThat(personnePhysiqueDto.getNom())
                .isNotNull()
                .isEqualTo("Nom");
        assertThat(personnePhysiqueDto.getPrenom())
                .isNotNull()
                .isEqualTo("Prénom");
        assertThat(personnePhysiqueDto.getTitre())
                .isNotNull()
                .isEqualTo(Titre.MONSIEUR.name());

        contact.setTitre(Titre.MADAME.name());
        contact.setNom("Nom 2");
        contact.setPrenom("Prénom 2");

        personnePhysiqueController.modify(contact);

        PersonnePhysiqueDto personnePhysiqueDto2 = personnePhysiqueController.get(contact.getId()).getBody();
        assertThat(personnePhysiqueDto2).isNotNull();
        assertThat(personnePhysiqueDto2.getNom())
                .isNotNull()
                .isEqualTo("Nom 2");
        assertThat(personnePhysiqueDto2.getPrenom())
                .isNotNull()
                .isEqualTo("Prénom 2");
        assertThat(personnePhysiqueDto2.getTitre())
                .isNotNull()
                .isEqualTo(Titre.MADAME.name());
    }

    @Test
    void delete() {
        PersonnePhysiqueDto contact = new PersonnePhysiqueDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prénom");

        contact = personnePhysiqueController.create(contact).getBody();

        assertThat(contact).isNotNull();

        personnePhysiqueController.delete(contact.getId());

        PersonnePhysiqueDto finalContact = contact;
        assertThatThrownBy(() -> personnePhysiqueController.get(finalContact.getId()))
                .isInstanceOf(SearchWithInexistingIdExceptionPerform.class);

        // Suppression inexistant
        assertThatThrownBy(() -> personnePhysiqueController.delete(1000L))
                .isInstanceOf(DeleteWithInexistingIdException.class);
    }
}