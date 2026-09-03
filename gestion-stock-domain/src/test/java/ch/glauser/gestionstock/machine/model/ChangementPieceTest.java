package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ChangementPieceTest {
    @Test
    void validateWithoutValue() {
        ChangementPiece changementPiece = new ChangementPiece();

        TestUtils.testValidation(changementPiece, ChangementPiece.class, 3);
    }

    @Test
    void validateWithValue() {
        Piece piece = new Piece();
        piece.setNom("Test");

        ChangementPiece changementPiece = new ChangementPiece();
        changementPiece.setPiece(piece);
        changementPiece.setDescription("Test");
        changementPiece.setQuantite(2);

        assertDoesNotThrow(changementPiece::validateCreate);
    }
}