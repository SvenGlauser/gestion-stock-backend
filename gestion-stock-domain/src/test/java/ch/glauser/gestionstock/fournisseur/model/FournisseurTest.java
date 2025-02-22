package ch.glauser.gestionstock.fournisseur.model;

import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FournisseurTest {
    @Test
    void validateWithoutValue() {
        Fournisseur fournisseur = new Fournisseur();

        TestUtils.testValidation(fournisseur, Fournisseur.class, 1);
    }

    @Test
    void validateWithValue() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom("Test");

        assertDoesNotThrow(fournisseur::validateCreate);
    }
}