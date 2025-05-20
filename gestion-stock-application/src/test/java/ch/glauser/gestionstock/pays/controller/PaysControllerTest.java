package ch.glauser.gestionstock.pays.controller;

import ch.glauser.gestionstock.GestionStockApplication;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.validation.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.localite.controller.LocaliteController;
import ch.glauser.gestionstock.localite.dto.LocaliteDto;
import ch.glauser.gestionstock.pays.dto.PaysDto;
import ch.glauser.gestionstock.utils.TestUtils;
import org.assertj.core.api.Assertions;
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
class PaysControllerTest {

    @Autowired
    PaysController paysController;

    @Autowired
    LocaliteController localiteController;

    @Test
    void get() {
        PaysDto pays = new PaysDto();
        pays.setNom("Pays - Test Get");
        pays.setAbreviation("Pays - Test Get - Abreviation");

        pays = paysController.create(pays).getBody();

        assertThat(pays).isNotNull();

        PaysDto paysDto = paysController.get(pays.getId()).getBody();
        assertThat(paysDto).isNotNull();
        assertThat(paysDto.getNom())
                .isNotNull()
                .isEqualTo("Pays - Test Get");
        assertThat(paysDto.getAbreviation())
                .isNotNull()
                .isEqualTo("Pays - Test Get - Abreviation");
    }

    @Test
    void create() {
        // Test validation bien mise en place
        PaysDto pays = new PaysDto();
        TestUtils.testValidation(2, () -> paysController.create(pays));

        // Test cas OK
        pays.setNom("Pays - Test Create");
        pays.setAbreviation("Pays - Test Create - Abreviation");
        assertDoesNotThrow(() -> paysController.create(pays));

        PaysDto pays2 = new PaysDto();
        pays2.setNom("Pays - Test Create");
        pays2.setAbreviation("Pays - Test Create - Abreviation - 2");

        // Test unicité du nom
        TestUtils.testValidation(1, () -> paysController.create(pays2));

        PaysDto pays3 = new PaysDto();
        pays3.setNom("Pays - Test Create - 2");
        pays3.setAbreviation("Pays - Test Create - Abreviation");

        // Test unicité de l'abréviation
        TestUtils.testValidation(1, () -> paysController.create(pays3));

        PaysDto pays4 = new PaysDto();
        pays4.setNom("Pays - Test Create - 2");
        pays4.setAbreviation("Pays - Test Create - Abreviation - 2");

        // Test unicité
        assertDoesNotThrow(() -> paysController.create(pays4));
    }

    @Test
    void search() {
        PaysDto pays = new PaysDto();
        pays.setNom("Pays - Test Search");
        pays.setAbreviation("Pays - Test Search - Abreviation");
        assertDoesNotThrow(() -> paysController.create(pays));

        PaysDto pays2 = new PaysDto();
        pays2.setNom("Pays - Test Search - 2");
        pays2.setAbreviation("Pays - Test Search - Abreviation - 2");
        assertDoesNotThrow(() -> paysController.create(pays2));

        PaysDto pays3 = new PaysDto();
        pays3.setNom("Pays - Test Search - 3");
        pays3.setAbreviation("Pays - Test Search - Abreviation - 3");
        assertDoesNotThrow(() -> paysController.create(pays3));

        SearchRequest searchRequest = new SearchRequest();
        
        SearchResult<PaysDto> result = paysController.search(searchRequest).getBody();
        assertThat(result).isNotNull();
        assertThat(result.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        Filter nom = new Filter();
        nom.setValue("Pays - Test Search - 3");
        nom.setField("nom");
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setCombinators(List.of(FilterCombinator.and(List.of(nom))));
        SearchResult<PaysDto> result2 = paysController.search(searchRequest2).getBody();
        assertThat(result2).isNotNull();
        assertThat(result2.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void modify() {
        PaysDto pays = new PaysDto();
        pays.setNom("Pays - Test Modify");
        pays.setAbreviation("Pays - Test Modify - Abreviation");

        pays = paysController.create(pays).getBody();

        assertThat(pays).isNotNull();

        PaysDto paysDto = paysController.get(pays.getId()).getBody();
        assertThat(paysDto).isNotNull();
        assertThat(paysDto.getNom())
                .isNotNull()
                .isEqualTo("Pays - Test Modify");
        assertThat(paysDto.getAbreviation())
                .isNotNull()
                .isEqualTo("Pays - Test Modify - Abreviation");

        pays.setNom("Pays - Test Modify - 2");
        pays.setAbreviation("Pays - Test Modify - Abreviation - 2");

        paysController.modify(pays);

        PaysDto paysDto2 = paysController.get(pays.getId()).getBody();
        assertThat(paysDto2).isNotNull();
        assertThat(paysDto2.getNom())
                .isNotNull()
                .isEqualTo("Pays - Test Modify - 2");
        assertThat(paysDto2.getAbreviation())
                .isNotNull()
                .isEqualTo("Pays - Test Modify - Abreviation - 2");
        assertThat(paysDto2.getId()).isEqualTo(paysDto.getId());
    }

    @Test
    void modifyAvecNomUnique() {
        PaysDto pays = new PaysDto();
        pays.setNom("Pays - Test ModifyAvecNomUnique");
        pays.setAbreviation("Pays - Test ModifyAvecNomUnique - Abreviation");

        pays = paysController.create(pays).getBody();

        assertThat(pays).isNotNull();

        PaysDto pays2 = new PaysDto();
        pays2.setNom("Pays - Test ModifyAvecNomUnique - 2");
        pays2.setAbreviation("Pays - Test ModifyAvecNomUnique - Abreviation - 2");

        pays2 = paysController.create(pays2).getBody();

        assertThat(pays2).isNotNull();

        PaysDto pays2SameName = pays2;
        pays2SameName.setNom("Pays - Test ModifyAvecNomUnique");
        TestUtils.testValidation(1, () -> paysController.modify(pays2SameName));

        PaysDto pays2SameAbreviation = pays2;
        pays2SameName.setNom("Pays - Test ModifyAvecNomUnique - 2");
        pays2SameAbreviation.setAbreviation("Pays - Test ModifyAvecNomUnique - Abreviation");
        TestUtils.testValidation(1, () -> paysController.modify(pays2SameAbreviation));
    }

    @Test
    void delete() {
        PaysDto pays = new PaysDto();
        pays.setNom("Pays - Test Delete");
        pays.setAbreviation("Pays - Test Delete - Abreviation");

        pays = paysController.create(pays).getBody();

        assertThat(pays).isNotNull();

        paysController.delete(pays.getId());

        final PaysDto finalPays1 = pays;
        assertThatThrownBy(() -> paysController.get(finalPays1.getId()))
                .isInstanceOf(SearchWithInexistingIdExceptionPerform.class);

        // Suppression inexistant
        assertThatThrownBy(() -> paysController.delete(1000L))
                .isInstanceOf(DeleteWithInexistingIdException.class);

        PaysDto pays2 = new PaysDto();
        pays2.setNom("Pays - Test Delete - 2");
        pays2.setAbreviation("Pays - Test Delete - Abreviation - 2");
        pays2 = paysController.create(pays2).getBody();

        LocaliteDto localiteDto = new LocaliteDto();
        localiteDto.setNom("Localite - Test Delete - 2");
        localiteDto.setNpa("Localite - Test Delete - 2398479 - 2");
        localiteDto.setPays(pays2);
        localiteDto = localiteController.create(localiteDto).getBody();

        // Suppression piece
        PaysDto finalPays = pays2;
        LocaliteDto finalLocalite = localiteDto;
        Assertions.assertThat(finalPays).isNotNull();
        Assertions.assertThat(finalLocalite).isNotNull();
        TestUtils.testValidation(1, () -> paysController.delete(finalPays.getId()));

        assertDoesNotThrow(() -> localiteController.delete(finalLocalite.getId()));
        assertDoesNotThrow(() -> paysController.delete(finalPays.getId()));
    }
}