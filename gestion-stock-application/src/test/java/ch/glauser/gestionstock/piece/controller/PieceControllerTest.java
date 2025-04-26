package ch.glauser.gestionstock.piece.controller;

import ch.glauser.gestionstock.GestionStockApplication;
import ch.glauser.gestionstock.categorie.controller.CategorieController;
import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.contact.controller.ContactController;
import ch.glauser.gestionstock.contact.dto.ContactDto;
import ch.glauser.gestionstock.contact.model.Titre;
import ch.glauser.gestionstock.fournisseur.controller.FournisseurController;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;
import ch.glauser.gestionstock.machine.controller.MachineController;
import ch.glauser.gestionstock.machine.dto.MachineDto;
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
class PieceControllerTest {

    @Autowired
    PieceController pieceController;

    @Autowired
    CategorieController categorieController;

    @Autowired
    ContactController contactController;

    @Autowired
    MachineController machineController;

    @Autowired
    FournisseurController fournisseurController;

    @Test
    void get() {
        FournisseurDto fournisseur = this.getFournisseur();

        PieceDto piece = new PieceDto();
        piece.setNumeroInventaire("12345");
        piece.setFournisseur(fournisseur);
        piece.setNom("Piece");
        piece.setDescription("Description");
        piece.setCategorie(this.getCategorie());
        piece.setQuantite(0L);
        piece.setPrix(1D);

        piece = pieceController.create(piece).getBody();

        assertThat(piece).isNotNull();

        PieceDto pieceDto = pieceController.get(piece.getId()).getBody();
        assertThat(pieceDto).isNotNull();
        assertThat(pieceDto.getNom())
                .isNotNull()
                .isEqualTo("Piece");
        assertThat(pieceDto.getDescription())
                .isNotNull()
                .isEqualTo("Description");
        assertThat(pieceDto.getNumeroInventaire())
                .isNotNull()
                .isEqualTo("12345");
    }

    @Test
    void create() {
        CategorieDto categorie = this.getCategorie();
        FournisseurDto fournisseur = this.getFournisseur();

        // Test validation bien mise en place
        PieceDto piece = new PieceDto();
        TestUtils.testValidation(6, () -> pieceController.create(piece));

        // Test cas OK
        piece.setNumeroInventaire("12345");
        piece.setNom("Piece");
        piece.setDescription("Description");
        piece.setCategorie(categorie);
        piece.setFournisseur(fournisseur);
        piece.setQuantite(0L);
        piece.setPrix(1D);
        assertDoesNotThrow(() -> pieceController.create(piece));

        PieceDto piece3 = new PieceDto();
        piece3.setNumeroInventaire("12345");
        piece3.setNom("Piece 2");
        piece3.setDescription("Description");
        piece3.setCategorie(categorie);
        piece3.setFournisseur(fournisseur);
        piece3.setQuantite(0L);
        piece3.setPrix(1D);

        // Test unicité du nom
        TestUtils.testValidation(1, () -> pieceController.create(piece3));

        PieceDto piece4 = new PieceDto();
        piece4.setNumeroInventaire("123456");
        piece4.setNom("Piece 2");
        piece4.setDescription("Description");
        piece4.setCategorie(categorie);
        piece4.setFournisseur(fournisseur);
        piece4.setQuantite(0L);
        piece4.setPrix(1D);

        // Test unicité du nom
        assertDoesNotThrow(() -> pieceController.create(piece4));
    }

    @Test
    void search() {
        CategorieDto categorie = this.getCategorie();
        CategorieDto categorie1 = this.getCategorie1();
        FournisseurDto fournisseur = this.getFournisseur();

        PieceDto piece = new PieceDto();
        piece.setNumeroInventaire("1");
        piece.setNom("Piece");
        piece.setDescription("Description");
        piece.setCategorie(categorie);
        piece.setFournisseur(fournisseur);
        piece.setQuantite(0L);
        piece.setPrix(1D);
        assertDoesNotThrow(() -> pieceController.create(piece));

        PieceDto piece2 = new PieceDto();
        piece2.setNumeroInventaire("2");
        piece2.setNom("Piece 2");
        piece2.setDescription("Description");
        piece2.setCategorie(categorie1);
        piece2.setFournisseur(fournisseur);
        piece2.setQuantite(0L);
        piece2.setPrix(1D);
        assertDoesNotThrow(() -> pieceController.create(piece2));

        PieceDto piece3 = new PieceDto();
        piece3.setNumeroInventaire("3");
        piece3.setNom("Piece 3");
        piece3.setDescription("Description");
        piece3.setCategorie(categorie);
        piece3.setFournisseur(fournisseur);
        piece3.setQuantite(0L);
        piece3.setPrix(1D);
        assertDoesNotThrow(() -> pieceController.create(piece3));

        SearchRequest searchRequest = new SearchRequest();
        
        SearchResult<PieceDto> result = pieceController.search(searchRequest).getBody();
        assertThat(result).isNotNull();
        assertThat(result.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        Filter categorieFilter = new Filter();
        categorieFilter.setValue(categorie.getId());
        categorieFilter.setField("categorie.id");
        SearchRequest searchRequest1 = new SearchRequest();
        searchRequest1.setCombinators(List.of(FilterCombinator.and(List.of(categorieFilter))));
        SearchResult<PieceDto> result1 = pieceController.search(searchRequest1).getBody();
        assertThat(result1).isNotNull();
        assertThat(result1.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Filter nom = new Filter();
        nom.setValue("Piece");
        nom.setField("nom");
        nom.setType(Filter.Type.STRING_LIKE);
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setCombinators(List.of(FilterCombinator.and(List.of(nom))));
        SearchResult<PieceDto> result2 = pieceController.search(searchRequest2).getBody();
        assertThat(result2).isNotNull();
        assertThat(result2.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    void modify() {
        FournisseurDto fournisseur = this.getFournisseur();
        PieceDto piece = new PieceDto();
        piece.setNumeroInventaire("1");
        piece.setNom("Piece");
        piece.setDescription("Description");
        piece.setCategorie(this.getCategorie());
        piece.setFournisseur(fournisseur);
        piece.setQuantite(0L);
        piece.setPrix(1D);

        piece = pieceController.create(piece).getBody();

        assertThat(piece).isNotNull();

        PieceDto pieceDto = pieceController.get(piece.getId()).getBody();
        assertThat(pieceDto).isNotNull();
        assertThat(pieceDto.getNom())
                .isNotNull()
                .isEqualTo("Piece");
        assertThat(pieceDto.getDescription())
                .isNotNull()
                .isEqualTo("Description");

        piece.setNom("Piece 2");
        piece.setDescription("Description 2");

        pieceController.modify(piece);

        PieceDto pieceDto2 = pieceController.get(piece.getId()).getBody();
        assertThat(pieceDto2).isNotNull();
        assertThat(pieceDto2.getNom())
                .isNotNull()
                .isEqualTo("Piece 2");
        assertThat(pieceDto2.getDescription())
                .isNotNull()
                .isEqualTo("Description 2");
        assertThat(pieceDto2.getId()).isEqualTo(pieceDto.getId());
    }

    @Test
    void modifyAvecNumeroUnique() {
        FournisseurDto fournisseur = this.getFournisseur();

        PieceDto piece = new PieceDto();
        piece.setNumeroInventaire("1");
        piece.setNom("Piece");
        piece.setDescription("Description");
        piece.setCategorie(this.getCategorie());
        piece.setFournisseur(fournisseur);
        piece.setQuantite(0L);
        piece.setPrix(1D);

        piece = pieceController.create(piece).getBody();
        assertThat(piece).isNotNull();

        PieceDto piece1 = new PieceDto();
        piece1.setNumeroInventaire("11");
        piece1.setNom("Piece 1");
        piece1.setDescription("Description");
        piece1.setCategorie(this.getCategorie1());
        piece1.setFournisseur(fournisseur);
        piece1.setQuantite(0L);
        piece1.setPrix(1D);

        piece1 = pieceController.create(piece1).getBody();

        assertThat(piece1).isNotNull();

        PieceDto piece1SameName = piece1;
        piece1SameName.setNumeroInventaire("1");
        TestUtils.testValidation(1, () -> pieceController.modify(piece1SameName));

        piece1SameName.setNumeroInventaire("2");
        assertDoesNotThrow(() -> pieceController.modify(piece1SameName));
    }

    @Test
    void delete() {
        FournisseurDto fournisseur = this.getFournisseur();

        PieceDto piece = new PieceDto();
        piece.setNumeroInventaire("1");
        piece.setNom("Piece");
        piece.setDescription("Description");
        piece.setCategorie(this.getCategorie());
        piece.setFournisseur(fournisseur);
        piece.setQuantite(0L);
        piece.setPrix(1D);

        piece = pieceController.create(piece).getBody();

        assertThat(piece).isNotNull();

        pieceController.delete(piece.getId());

        piece = pieceController.get(piece.getId()).getBody();

        assertThat(piece).isNull();

        // Suppression inexistant
        TestUtils.testValidation(1, () -> pieceController.delete(1000L));

        PieceDto piece2 = new PieceDto();
        piece2.setNumeroInventaire("2");
        piece2.setNom("Piece 2");
        piece2.setDescription("Description");
        piece2.setCategorie(this.getCategorie1());
        piece2.setFournisseur(fournisseur);
        piece2.setQuantite(0L);
        piece2.setPrix(1D);
        piece2 = pieceController.create(piece2).getBody();
        assertThat(piece2).isNotNull();

        ContactDto contactDto = new ContactDto();
        contactDto.setNom("Contact");
        contactDto.setPrenom("Prenom");
        contactDto.setTitre(Titre.MONSIEUR.name());
        contactDto = contactController.create(contactDto).getBody();

        MachineDto machineDto = new MachineDto();
        machineDto.setNom("Machine");
        machineDto.setContact(contactDto);
        machineDto.setPieces(List.of(piece2));
        machineDto = machineController.create(machineDto).getBody();

        // Suppression piece
        PieceDto finalPiece = piece2;
        MachineDto finalMachine = machineDto;
        Assertions.assertThat(finalPiece).isNotNull();
        Assertions.assertThat(finalMachine).isNotNull();
        TestUtils.testValidation(1, () -> pieceController.delete(finalPiece.getId()));

        assertDoesNotThrow(() -> machineController.delete(finalMachine.getId()));
        assertDoesNotThrow(() -> pieceController.delete(finalPiece.getId()));
    }

    private CategorieDto getCategorie() {
        CategorieDto categorie = new CategorieDto();
        categorie.setNom("Categorie");
        categorie.setActif(true);
        return categorieController.create(categorie).getBody();
    }

    private CategorieDto getCategorie1() {
        CategorieDto categorie = new CategorieDto();
        categorie.setNom("Categorie 1");
        categorie.setActif(true);
        return categorieController.create(categorie).getBody();
    }

    private FournisseurDto getFournisseur() {
        FournisseurDto fournisseurDto = new FournisseurDto();
        fournisseurDto.setNom("Test");
        return this.fournisseurController.create(fournisseurDto).getBody();
    }
}