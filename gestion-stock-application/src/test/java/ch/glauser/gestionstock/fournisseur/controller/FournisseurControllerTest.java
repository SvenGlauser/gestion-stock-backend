package ch.glauser.gestionstock.fournisseur.controller;

import ch.glauser.gestionstock.GestionStockApplication;
import ch.glauser.gestionstock.categorie.controller.CategorieController;
import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = GestionStockApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FournisseurControllerTest {

    @Autowired
    FournisseurController fournisseurController;

    @Autowired
    PieceController pieceController;

    @Autowired
    CategorieController categorieController;

    @Test
    void get() {
        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setNom("Fournisseur");
        fournisseur.setDescription("Description");
        fournisseur.setUrl("https://google.com");

        fournisseur = fournisseurController.create(fournisseur).getBody();

        assertThat(fournisseur).isNotNull();

        FournisseurDto fournisseurDto = fournisseurController.get(fournisseur.getId()).getBody();
        assertThat(fournisseurDto).isNotNull();
        assertThat(fournisseurDto.getNom())
                .isNotNull()
                .isEqualTo("Fournisseur");
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
        fournisseur.setNom("Fournisseur");
        assertDoesNotThrow(() -> fournisseurController.create(fournisseur));

        FournisseurDto fournisseur2 = new FournisseurDto();
        fournisseur2.setNom("Fournisseur");

        // Test unicité du nom
        TestUtils.testValidation(1, () -> fournisseurController.create(fournisseur2));

        FournisseurDto fournisseur3 = new FournisseurDto();
        fournisseur3.setNom("Fournisseur 2");

        // Test unicité du nom
        assertDoesNotThrow(() -> fournisseurController.create(fournisseur3));
    }

    @Test
    void search() {
        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setNom("Fournisseur");
        fournisseur.setDescription("Description");
        assertDoesNotThrow(() -> fournisseurController.create(fournisseur));

        FournisseurDto fournisseur2 = new FournisseurDto();
        fournisseur2.setNom("Fournisseur 2");
        fournisseur2.setDescription("Description");
        assertDoesNotThrow(() -> fournisseurController.create(fournisseur2));

        FournisseurDto fournisseur3 = new FournisseurDto();
        fournisseur3.setNom("Fournisseur 3");
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
        searchRequest1.setFilters(List.of(description));
        SearchResult<FournisseurDto> result1 = fournisseurController.search(searchRequest1).getBody();
        assertThat(result1).isNotNull();
        assertThat(result1.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Filter nom = new Filter();
        nom.setValue("Fournisseur");
        nom.setField("nom");
        nom.setType(Filter.Type.STRING_LIKE);
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setFilters(List.of(nom));
        SearchResult<FournisseurDto> result2 = fournisseurController.search(searchRequest2).getBody();
        assertThat(result2).isNotNull();
        assertThat(result2.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    void modify() {
        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setNom("Fournisseur");
        fournisseur.setDescription("Description");
        fournisseur.setUrl("https://google.com");

        fournisseur = fournisseurController.create(fournisseur).getBody();

        assertThat(fournisseur).isNotNull();

        FournisseurDto fournisseurDto = fournisseurController.get(fournisseur.getId()).getBody();
        assertThat(fournisseurDto).isNotNull();
        assertThat(fournisseurDto.getNom())
                .isNotNull()
                .isEqualTo("Fournisseur");
        assertThat(fournisseurDto.getDescription())
                .isNotNull()
                .isEqualTo("Description");
        assertThat(fournisseurDto.getUrl())
                .isNotNull()
                .isEqualTo("https://google.com");

        fournisseur.setNom("Fournisseur 2");
        fournisseur.setDescription("Description 2");
        fournisseur.setUrl("https://google.ch");

        fournisseurController.modify(fournisseur).getBody();

        FournisseurDto fournisseurDto2 = fournisseurController.get(fournisseur.getId()).getBody();
        assertThat(fournisseurDto2).isNotNull();
        assertThat(fournisseurDto2.getNom())
                .isNotNull()
                .isEqualTo("Fournisseur 2");
        assertThat(fournisseurDto2.getDescription())
                .isNotNull()
                .isEqualTo("Description 2");
        assertThat(fournisseurDto2.getUrl())
                .isNotNull()
                .isEqualTo("https://google.ch");
    }

    @Test
    void modifyAvecNomUnique() {
        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setNom("Fournisseur");

        fournisseur = fournisseurController.create(fournisseur).getBody();

        assertThat(fournisseur).isNotNull();

        FournisseurDto fournisseur2 = new FournisseurDto();
        fournisseur2.setNom("Fournisseur 2");

        fournisseur2 = fournisseurController.create(fournisseur2).getBody();

        assertThat(fournisseur2).isNotNull();

        FournisseurDto fournisseur2SameName = fournisseur2;
        fournisseur2SameName.setNom("Fournisseur");
        TestUtils.testValidation(1, () -> fournisseurController.modify(fournisseur2SameName));
    }

    @Test
    void delete() {
        FournisseurDto fournisseur = new FournisseurDto();
        fournisseur.setNom("Fournisseur");

        fournisseur = fournisseurController.create(fournisseur).getBody();

        assertThat(fournisseur).isNotNull();

        fournisseurController.delete(fournisseur.getId());

        fournisseur = fournisseurController.get(fournisseur.getId()).getBody();

        assertThat(fournisseur).isNull();

        // Suppression inexistant
        TestUtils.testValidation(1, () -> fournisseurController.delete(1000L));

        FournisseurDto fournisseur2 = new FournisseurDto();
        fournisseur2.setNom("Fournisseur 2");
        fournisseur2 = fournisseurController.create(fournisseur2).getBody();

        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setNom("Categorie");
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