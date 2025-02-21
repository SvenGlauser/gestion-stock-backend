package ch.glauser.gestionstock.piece.model;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PieceTest {
    @Test
    void validateWithoutValue() {
        Piece piece = new Piece();

        TestUtils.testValidation(piece, Piece.class, 5);
    }

    @Test
    void validateWithValue() {
        Piece piece = new Piece();
        piece.setNom("Test");
        piece.setNumeroInventaire("9807234-123");
        piece.setPrix(2.50);
        piece.setQuantite(123L);
        piece.setCategorie(new Categorie());

        assertDoesNotThrow(piece::validateCreate);
    }
}