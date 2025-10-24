package ch.glauser.gestionstock.categorie.controller;

import ch.glauser.gestionstock.GestionStockApplication;
import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.controller.FournisseurController;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;
import ch.glauser.gestionstock.identite.controller.PersonneMoraleController;
import ch.glauser.gestionstock.identite.dto.PersonneMoraleDto;
import ch.glauser.gestionstock.identite.model.PersonneMorale;
import ch.glauser.gestionstock.piece.controller.PieceController;
import ch.glauser.gestionstock.piece.dto.PieceDto;
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
class CategorieControllerTest {

    @Autowired
    CategorieController categorieController;

    @Autowired
    PieceController pieceController;

    @Autowired
    FournisseurController fournisseurController;

    @Autowired
    PersonneMoraleController personneMoraleController;

    @Test
    void get() {
        CategorieDto categorie = new CategorieDto();
        categorie.setNom("Categorie - Test Get");
        categorie.setDescription("Categorie - Test Get - Description");
        categorie.setActif(false);

        categorie = categorieController.create(categorie).getBody();

        assertThat(categorie).isNotNull();

        CategorieDto categorieDto = categorieController.get(categorie.getId()).getBody();
        assertThat(categorieDto).isNotNull();
        assertThat(categorieDto.getNom())
                .isNotNull()
                .isEqualTo("Categorie - Test Get");
        assertThat(categorieDto.getDescription())
                .isNotNull()
                .isEqualTo("Categorie - Test Get - Description");
        assertThat(categorieDto.getActif()).isFalse();
    }

    @Test
    void create() {
        // Test validation bien mise en place
        CategorieDto categorie = new CategorieDto();
        TestUtils.testValidation(2, () -> categorieController.create(categorie));

        // Test cas OK
        categorie.setNom("Categorie - Test Set");
        categorie.setActif(true);
        assertDoesNotThrow(() -> categorieController.create(categorie));

        CategorieDto categorie2 = new CategorieDto();
        categorie2.setNom("Categorie - Test Set");
        categorie2.setActif(true);

        // Test unicité du nom
        TestUtils.testValidation(1, () -> categorieController.create(categorie2));

        CategorieDto categorie3 = new CategorieDto();
        categorie3.setNom("Categorie - Test Set - 2");
        categorie3.setActif(true);

        // Test unicité du nom
        assertDoesNotThrow(() -> categorieController.create(categorie3));
    }

    @Test
    void search() {
        CategorieDto categorie = new CategorieDto();
        categorie.setNom("Categorie - Test Search");
        categorie.setActif(false);
        assertDoesNotThrow(() -> categorieController.create(categorie));

        CategorieDto categorie2 = new CategorieDto();
        categorie2.setNom("Categorie - Test Search - 2");
        categorie2.setActif(false);
        assertDoesNotThrow(() -> categorieController.create(categorie2));

        CategorieDto categorie3 = new CategorieDto();
        categorie3.setNom("Categorie - Test Search - 3");
        categorie3.setActif(true);
        assertDoesNotThrow(() -> categorieController.create(categorie3));

        SearchRequest searchRequest = new SearchRequest();
        
        SearchResult<CategorieDto> result = categorieController.search(searchRequest).getBody();
        assertThat(result).isNotNull();
        assertThat(result.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        Filter actif = new Filter();
        actif.setValue(false);
        actif.setField("actif");
        SearchRequest searchRequest1 = new SearchRequest();
        searchRequest1.setCombinators(List.of(FilterCombinator.and(List.of(actif))));
        SearchResult<CategorieDto> result1 = categorieController.search(searchRequest1).getBody();
        assertThat(result1).isNotNull();
        assertThat(result1.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Filter nom = new Filter();
        nom.setValue("Categorie - Test Search - 3");
        nom.setField("nom");
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setCombinators(List.of(FilterCombinator.and(List.of(nom))));
        SearchResult<CategorieDto> result2 = categorieController.search(searchRequest2).getBody();
        assertThat(result2).isNotNull();
        assertThat(result2.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void modify() {
        CategorieDto categorie = new CategorieDto();
        categorie.setNom("Categorie - Test Modify");
        categorie.setDescription("Categorie - Test Modify - Description");
        categorie.setActif(false);

        categorie = categorieController.create(categorie).getBody();

        assertThat(categorie).isNotNull();

        CategorieDto categorieDto = categorieController.get(categorie.getId()).getBody();
        assertThat(categorieDto).isNotNull();
        assertThat(categorieDto.getNom())
                .isNotNull()
                .isEqualTo("Categorie - Test Modify");
        assertThat(categorieDto.getDescription())
                .isNotNull()
                .isEqualTo("Categorie - Test Modify - Description");
        assertThat(categorieDto.getActif()).isFalse();

        categorie.setNom("Categorie - Test Modify - 2");
        categorie.setDescription("Categorie - Test Modify - Description - 2");
        categorie.setActif(true);

        categorieController.modify(categorie);

        CategorieDto categorieDto2 = categorieController.get(categorie.getId()).getBody();
        assertThat(categorieDto2).isNotNull();
        assertThat(categorieDto2.getNom())
                .isNotNull()
                .isEqualTo("Categorie - Test Modify - 2");
        assertThat(categorieDto2.getDescription())
                .isNotNull()
                .isEqualTo("Categorie - Test Modify - Description - 2");
        assertThat(categorieDto2.getActif()).isTrue();
        assertThat(categorieDto2.getId()).isEqualTo(categorieDto.getId());
    }

    @Test
    void modifyAvecNomUnique() {
        CategorieDto categorie = new CategorieDto();
        categorie.setNom("Categorie - Test ModifyAvecNomUnique");
        categorie.setActif(true);

        categorie = categorieController.create(categorie).getBody();

        assertThat(categorie).isNotNull();

        CategorieDto categorie2 = new CategorieDto();
        categorie2.setNom("Categorie - Test ModifyAvecNomUnique - 2");
        categorie2.setActif(true);

        categorie2 = categorieController.create(categorie2).getBody();

        assertThat(categorie2).isNotNull();

        CategorieDto categorie2SameName = categorie2;
        categorie2SameName.setNom("Categorie - Test ModifyAvecNomUnique");
        TestUtils.testValidation(1, () -> categorieController.modify(categorie2SameName));
    }

    @Test
    void delete() {
        CategorieDto categorie = new CategorieDto();
        categorie.setNom("Categorie - Test Delete");
        categorie.setActif(true);

        categorie = categorieController.create(categorie).getBody();

        assertThat(categorie).isNotNull();

        categorieController.delete(categorie.getId());

        CategorieDto finalCategorie1 = categorie;
        assertThatThrownBy(() -> categorieController.get(finalCategorie1.getId()))
                .isInstanceOf(SearchWithInexistingIdExceptionPerform.class);

        // Suppression inexistant
        assertThatThrownBy(() -> categorieController.delete(1000L))
                .isInstanceOf(DeleteWithInexistingIdException.class);

        CategorieDto categorie2 = new CategorieDto();
        categorie2.setNom("Categorie - Test Delete - 2");
        categorie2.setActif(true);
        categorie2 = categorieController.create(categorie2).getBody();

        PersonneMoraleDto personneMorale = new PersonneMoraleDto();
        personneMorale.setRaisonSociale("Personne Morale - Test Delete - 2");
        personneMorale = this.personneMoraleController.create(personneMorale).getBody();

        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setIdentite(personneMorale);
        fournisseur = this.fournisseurController.create(fournisseur).getBody();

        PieceDto piece = new PieceDto();
        piece.setNom("Piece - Test Delete - 2");
        piece.setNumeroInventaire("12345678");
        piece.setCategorie(categorie2);
        piece.setFournisseur(fournisseur);
        piece.setQuantite(0L);
        piece.setPrix(0D);
        piece = pieceController.create(piece).getBody();

        // Suppression piece
        CategorieDto finalCategorie = categorie2;
        PieceDto finalPiece = piece;
        Assertions.assertThat(finalCategorie).isNotNull();
        Assertions.assertThat(finalPiece).isNotNull();
        TestUtils.testValidation(1, () -> categorieController.delete(finalCategorie.getId()));

        assertDoesNotThrow(() -> pieceController.delete(finalPiece.getId()));
        assertDoesNotThrow(() -> categorieController.delete(finalCategorie.getId()));
    }
}