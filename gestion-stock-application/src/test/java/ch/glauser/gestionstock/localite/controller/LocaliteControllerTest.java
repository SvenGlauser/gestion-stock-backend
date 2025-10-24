package ch.glauser.gestionstock.localite.controller;

import ch.glauser.gestionstock.GestionStockApplication;
import ch.glauser.gestionstock.adresse.dto.AdresseDto;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.controller.PersonnePhysiqueController;
import ch.glauser.gestionstock.identite.dto.PersonnePhysiqueDto;
import ch.glauser.gestionstock.identite.model.Titre;
import ch.glauser.gestionstock.localite.dto.LocaliteDto;
import ch.glauser.gestionstock.pays.controller.PaysController;
import ch.glauser.gestionstock.pays.dto.PaysDto;
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
class LocaliteControllerTest {

    @Autowired
    LocaliteController localiteController;

    @Autowired
    PaysController paysController;
    @Autowired
    PersonnePhysiqueController personnePhysiqueController;

    @Test
    void get() {
        LocaliteDto localite = new LocaliteDto();
        localite.setNom("Localite");
        localite.setNpa("1234");
        localite.setPays(this.getPays());

        localite = localiteController.create(localite).getBody();

        assertThat(localite).isNotNull();

        LocaliteDto localiteDto = localiteController.get(localite.getId()).getBody();
        assertThat(localiteDto).isNotNull();
        assertThat(localiteDto.getNom())
                .isNotNull()
                .isEqualTo("Localite");
        assertThat(localiteDto.getNpa())
                .isNotNull()
                .isEqualTo("1234");
        assertThat(localiteDto.getPays())
                .isNotNull();
    }

    @Test
    void create() {
        PaysDto pays = this.getPays();

        // Test validation bien mise en place
        LocaliteDto localite = new LocaliteDto();
        TestUtils.testValidation(3, () -> localiteController.create(localite));

        // Test cas OK
        localite.setNom("Localite");
        localite.setNpa("1234");
        localite.setPays(pays);
        assertDoesNotThrow(() -> localiteController.create(localite));

        LocaliteDto localite2 = new LocaliteDto();
        localite2.setNom("Localite");
        localite2.setNpa("1234");
        localite2.setPays(pays);

        // Test unicité du nom
        TestUtils.testValidation(1, () -> localiteController.create(localite2));

        LocaliteDto localite3 = new LocaliteDto();
        localite3.setNom("Localite");
        localite3.setNpa("1234");
        localite3.setPays(this.getPays2());

        // Test unicité du nom
        assertDoesNotThrow(() -> localiteController.create(localite3));

        LocaliteDto localite4 = new LocaliteDto();
        localite4.setNom("Localite");
        localite4.setNpa("12345");
        localite4.setPays(pays);

        // Test unicité du nom
        assertDoesNotThrow(() -> localiteController.create(localite4));
    }

    @Test
    void search() {
        PaysDto pays = this.getPays();

        LocaliteDto localite = new LocaliteDto();
        localite.setNom("Localite");
        localite.setNpa("1234");
        localite.setPays(pays);
        assertDoesNotThrow(() -> localiteController.create(localite));

        LocaliteDto localite2 = new LocaliteDto();
        localite2.setNom("Localite 2");
        localite2.setNpa("12345");
        localite2.setPays(pays);
        assertDoesNotThrow(() -> localiteController.create(localite2));

        LocaliteDto localite3 = new LocaliteDto();
        localite3.setNom("Localite");
        localite3.setNpa("123456");
        localite3.setPays(this.getPays2());
        assertDoesNotThrow(() -> localiteController.create(localite3));

        SearchRequest searchRequest = new SearchRequest();
        
        SearchResult<LocaliteDto> result = localiteController.search(searchRequest).getBody();
        assertThat(result).isNotNull();
        assertThat(result.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        Filter paysFilter = new Filter();
        paysFilter.setValue(pays.getId());
        paysFilter.setField("pays.id");
        SearchRequest searchRequest1 = new SearchRequest();
        searchRequest1.setCombinators(List.of(FilterCombinator.and(List.of(paysFilter))));
        SearchResult<LocaliteDto> result1 = localiteController.search(searchRequest1).getBody();
        assertThat(result1).isNotNull();
        assertThat(result1.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Filter nom = new Filter();
        nom.setValue("Localite");
        nom.setField("nom");
        Filter npa = new Filter();
        npa.setValue("1234");
        npa.setField("npa");
        npa.setType(Filter.Type.STRING_LIKE);
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setCombinators(List.of(FilterCombinator.and(List.of(npa, nom))));
        SearchResult<LocaliteDto> result2 = localiteController.search(searchRequest2).getBody();
        assertThat(result2).isNotNull();
        assertThat(result2.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void modify() {
        LocaliteDto localite = new LocaliteDto();
        localite.setNom("Localite");
        localite.setNpa("1234");
        localite.setPays(this.getPays());

        localite = localiteController.create(localite).getBody();

        assertThat(localite).isNotNull();

        LocaliteDto localiteDto = localiteController.get(localite.getId()).getBody();
        assertThat(localiteDto).isNotNull();
        assertThat(localiteDto.getNom())
                .isNotNull()
                .isEqualTo("Localite");
        assertThat(localiteDto.getNpa())
                .isNotNull()
                .isEqualTo("1234");
        assertThat(localiteDto.getPays())
                .isNotNull();

        localite.setNom("Localite 2");
        localite.setNpa("12345");

        localiteController.modify(localite);

        LocaliteDto localiteDto2 = localiteController.get(localite.getId()).getBody();
        assertThat(localiteDto2).isNotNull();
        assertThat(localiteDto2.getNom())
                .isNotNull()
                .isEqualTo("Localite 2");
        assertThat(localiteDto2.getNpa())
                .isNotNull()
                .isEqualTo("12345");
        assertThat(localiteDto2.getId()).isEqualTo(localiteDto.getId());
    }

    @Test
    void modifyAvecNomUnique() {
        PaysDto pays1 = this.getPays();
        PaysDto pays2 = this.getPays2();

        LocaliteDto localite = new LocaliteDto();
        localite.setNom("Localite");
        localite.setNpa("1234");
        localite.setPays(pays1);

        localite = localiteController.create(localite).getBody();

        assertThat(localite).isNotNull();

        LocaliteDto localite2 = new LocaliteDto();
        localite2.setNom("Localite");
        localite2.setNpa("1234");
        localite2.setPays(pays2);

        localite2 = localiteController.create(localite2).getBody();

        assertThat(localite2).isNotNull();

        LocaliteDto localite1SameName = localite;
        localite1SameName.setPays(pays2);
        TestUtils.testValidation(1, () -> localiteController.modify(localite1SameName));
    }

    @Test
    void delete() {
        PaysDto pays = this.getPays();

        LocaliteDto localite = new LocaliteDto();
        localite.setNom("Localite");
        localite.setNpa("1234");
        localite.setPays(pays);

        localite = localiteController.create(localite).getBody();

        assertThat(localite).isNotNull();

        localiteController.delete(localite.getId());

        LocaliteDto finalLocalite = localite;
        assertThatThrownBy(() -> localiteController.get(finalLocalite.getId()))
                .isInstanceOf(SearchWithInexistingIdExceptionPerform.class);

        // Suppression inexistant
        assertThatThrownBy(() -> localiteController.delete(1000L))
                .isInstanceOf(DeleteWithInexistingIdException.class);

        LocaliteDto localite2 = new LocaliteDto();
        localite2.setNom("Localite");
        localite2.setNpa("1234");
        localite2.setPays(pays);
        localite2 = localiteController.create(localite2).getBody();

        AdresseDto adresse = new AdresseDto();
        adresse.setLocalite(localite2);

        PersonnePhysiqueDto identiteDto = new PersonnePhysiqueDto();
        identiteDto.setTitre(Titre.MADAME.name());
        identiteDto.setNom("Nom");
        identiteDto.setPrenom("Prenom");
        identiteDto.setAdresse(adresse);
        identiteDto = personnePhysiqueController.create(identiteDto).getBody();
        assertThat(identiteDto).isNotNull();

        personnePhysiqueController.delete(identiteDto.getId());

        LocaliteDto localite2Final = localite2;
        assertThat(localite2Final).isNotNull();
        assertDoesNotThrow(() -> localiteController.delete(localite2Final.getId()));
    }

    private PaysDto getPays() {
        PaysDto pays = new PaysDto();
        pays.setNom("Suisse");
        pays.setAbreviation("CH");

        return paysController.create(pays).getBody();
    }

    private PaysDto getPays2() {
        PaysDto pays = new PaysDto();
        pays.setNom("France");
        pays.setAbreviation("FR");

        return paysController.create(pays).getBody();
    }
}