package ch.glauser.gestionstock.fournisseur.controller;

import ch.glauser.gestionstock.categorie.controller.CategorieController;
import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;
import ch.glauser.gestionstock.identite.controller.PersonneMoraleController;
import ch.glauser.gestionstock.identite.dto.PersonneMoraleDto;
import ch.glauser.gestionstock.piece.controller.PieceController;
import ch.glauser.gestionstock.piece.dto.PieceDto;
import ch.glauser.gestionstock.utils.SecurityConfigurationTest;
import ch.glauser.gestionstock.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = SecurityConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithUserDetails(SecurityConfigurationTest.TEST_ADMIN_USERNAME)
class FournisseurControllerTest {

    @Autowired
    FournisseurController fournisseurController;

    @Autowired
    PieceController pieceController;

    @Autowired
    CategorieController categorieController;

    @Autowired
    PersonneMoraleController personneMoraleController;

    @Test
    void get() {
        PersonneMoraleDto personneMorale = new PersonneMoraleDto();
        personneMorale.setRaisonSociale("Identité lié au fournisseur");
        personneMorale = personneMoraleController.create(personneMorale).getBody();
        assertThat(personneMorale).isNotNull();

        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setIdentite(personneMorale);
        fournisseur.setDescription("Description");
        fournisseur.setUrl("https://google.com");

        fournisseur = fournisseurController.create(fournisseur).getBody();

        assertThat(fournisseur).isNotNull();

        FournisseurDto fournisseurDto = fournisseurController.get(fournisseur.getId()).getBody();
        assertThat(fournisseurDto).isNotNull();
        assertThat(fournisseurDto.getIdentite().getId())
                .isNotNull()
                .isEqualTo(personneMorale.getId());
        assertThat(fournisseurDto.getDescription())
                .isNotNull()
                .isEqualTo("Description");
        assertThat(fournisseurDto.getUrl())
                .isNotNull()
                .isEqualTo("https://google.com");
    }

    @Test
    void create() {
        // Test validation bien mise en place
        FournisseurDto fournisseur = new FournisseurDto();
        TestUtils.testValidation(1, () -> fournisseurController.create(fournisseur));

        // Test cas OK
        PersonneMoraleDto personneMorale = new PersonneMoraleDto();
        personneMorale.setRaisonSociale("Identité lié au fournisseur");
        personneMorale = personneMoraleController.create(personneMorale).getBody();
        assertThat(personneMorale).isNotNull();

        fournisseur.setIdentite(personneMorale);
        assertDoesNotThrow(() -> fournisseurController.create(fournisseur));

        FournisseurDto fournisseur2 = new FournisseurDto();
        fournisseur2.setIdentite(personneMorale);

        // Test unicité du nom
        TestUtils.testValidation(1, () -> fournisseurController.create(fournisseur2));

        PersonneMoraleDto personneMorale2 = new PersonneMoraleDto();
        personneMorale2.setRaisonSociale("Identité lié au fournisseur 2");
        personneMorale2 = personneMoraleController.create(personneMorale2).getBody();
        assertThat(personneMorale2).isNotNull();

        FournisseurDto fournisseur3 = new FournisseurDto();
        fournisseur3.setIdentite(personneMorale2);

        // Test unicité du nom
        assertDoesNotThrow(() -> fournisseurController.create(fournisseur3));
    }

    @Test
    void search() {
        PersonneMoraleDto personneMorale = new PersonneMoraleDto();
        personneMorale.setRaisonSociale("Identité lié au fournisseur");
        personneMorale = personneMoraleController.create(personneMorale).getBody();
        assertThat(personneMorale).isNotNull();

        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setIdentite(personneMorale);
        fournisseur.setDescription("Description");
        assertDoesNotThrow(() -> fournisseurController.create(fournisseur));

        PersonneMoraleDto personneMorale2 = new PersonneMoraleDto();
        personneMorale2.setRaisonSociale("Identité lié au fournisseur 2");
        personneMorale2 = personneMoraleController.create(personneMorale2).getBody();
        assertThat(personneMorale2).isNotNull();

        FournisseurDto fournisseur2 = new FournisseurDto();
        fournisseur2.setIdentite(personneMorale2);
        fournisseur2.setDescription("Description");
        assertDoesNotThrow(() -> fournisseurController.create(fournisseur2));

        PersonneMoraleDto personneMorale3 = new PersonneMoraleDto();
        personneMorale3.setRaisonSociale("Identité lié au fournisseur 2");
        personneMorale3 = personneMoraleController.create(personneMorale3).getBody();
        assertThat(personneMorale3).isNotNull();

        FournisseurDto fournisseur3 = new FournisseurDto();
        fournisseur3.setIdentite(personneMorale3);
        fournisseur3.setDescription("Description 2");
        assertDoesNotThrow(() -> fournisseurController.create(fournisseur3));

        SearchRequest searchRequest = new SearchRequest();
        
        SearchResult<FournisseurDto> result = fournisseurController.search(searchRequest).getBody();
        assertThat(result).isNotNull();
        assertThat(result.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        Filter description = new Filter();
        description.setValue("Description 2");
        description.setField("description");
        description.setType(Filter.Type.STRING_LIKE);
        SearchRequest searchRequest1 = new SearchRequest();
        searchRequest1.setCombinators(List.of(FilterCombinator.and(List.of(description))));
        SearchResult<FournisseurDto> result1 = fournisseurController.search(searchRequest1).getBody();
        assertThat(result1).isNotNull();
        assertThat(result1.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Filter nom = new Filter();
        nom.setValue("fournisseur");
        nom.setField("identite.designation");
        nom.setType(Filter.Type.STRING_LIKE);
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setCombinators(List.of(FilterCombinator.and(List.of(nom))));
        SearchResult<FournisseurDto> result2 = fournisseurController.search(searchRequest2).getBody();
        assertThat(result2).isNotNull();
        assertThat(result2.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    void modify() {
        PersonneMoraleDto personneMorale = new PersonneMoraleDto();
        personneMorale.setRaisonSociale("Identité lié au fournisseur");
        personneMorale = personneMoraleController.create(personneMorale).getBody();
        assertThat(personneMorale).isNotNull();

        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setIdentite(personneMorale);
        fournisseur.setDescription("Description");
        fournisseur.setUrl("https://google.com");

        fournisseur = fournisseurController.create(fournisseur).getBody();

        assertThat(fournisseur).isNotNull();

        FournisseurDto fournisseurDto = fournisseurController.get(fournisseur.getId()).getBody();
        assertThat(fournisseurDto).isNotNull();
        assertThat(fournisseurDto.getIdentite().getId())
                .isNotNull()
                .isEqualTo(personneMorale.getId());
        assertThat(fournisseurDto.getDescription())
                .isNotNull()
                .isEqualTo("Description");
        assertThat(fournisseurDto.getUrl())
                .isNotNull()
                .isEqualTo("https://google.com");

        PersonneMoraleDto personneMorale2 = new PersonneMoraleDto();
        personneMorale2.setRaisonSociale("Identité lié au fournisseur 2");
        personneMorale2 = personneMoraleController.create(personneMorale2).getBody();
        assertThat(personneMorale2).isNotNull();

        fournisseur.setIdentite(personneMorale2);
        fournisseur.setDescription("Description 2");
        fournisseur.setUrl("https://google.ch");

        fournisseurController.modify(fournisseur);

        FournisseurDto fournisseurDto2 = fournisseurController.get(fournisseur.getId()).getBody();
        assertThat(fournisseurDto2).isNotNull();
        assertThat(fournisseurDto2.getIdentite().getId())
                .isNotNull()
                .isEqualTo(personneMorale2.getId());
        assertThat(fournisseurDto2.getDescription())
                .isNotNull()
                .isEqualTo("Description 2");
        assertThat(fournisseurDto2.getUrl())
                .isNotNull()
                .isEqualTo("https://google.ch");
    }

    @Test
    void delete() {
        PersonneMoraleDto personneMorale = new PersonneMoraleDto();
        personneMorale.setRaisonSociale("Identité lié au fournisseur 2");
        personneMorale = personneMoraleController.create(personneMorale).getBody();
        assertThat(personneMorale).isNotNull();

        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setIdentite(personneMorale);

        fournisseur = fournisseurController.create(fournisseur).getBody();

        assertThat(fournisseur).isNotNull();

        fournisseurController.delete(fournisseur.getId());

        FournisseurDto finalFournisseur1 = fournisseur;
        assertThatThrownBy(() -> fournisseurController.get(finalFournisseur1.getId()))
                .isInstanceOf(SearchWithInexistingIdExceptionPerform.class);

        // Suppression inexistant
        assertThatThrownBy(() -> fournisseurController.delete(1000L))
                .isInstanceOf(DeleteWithInexistingIdException.class);

        FournisseurDto fournisseur2 = new FournisseurDto();
        fournisseur2.setIdentite(personneMorale);
        fournisseur2 = fournisseurController.create(fournisseur2).getBody();

        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setNom("Categorie");
        categorieDto.setActif(true);
        categorieDto = categorieController.create(categorieDto).getBody();
        PieceDto piece = new PieceDto();
        piece.setNom("Piece");
        piece.setNumeroInventaire("12345678");
        piece.setFournisseur(fournisseur2);
        piece.setCategorie(categorieDto);
        piece.setQuantite(0L);
        piece.setPrix(0D);
        piece = pieceController.create(piece).getBody();

        // Suppression piece
        FournisseurDto finalFournisseur = fournisseur2;
        PieceDto finalPiece = piece;
        Assertions.assertThat(finalFournisseur).isNotNull();
        Assertions.assertThat(finalPiece).isNotNull();
        TestUtils.testValidation(1, () -> fournisseurController.delete(finalFournisseur.getId()));

        assertDoesNotThrow(() -> pieceController.delete(finalPiece.getId()));
        assertDoesNotThrow(() -> fournisseurController.delete(finalFournisseur.getId()));
    }
}