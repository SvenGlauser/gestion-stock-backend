package ch.glauser.gestionstock.categorie.model;

import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CategorieTest {

    @Test
    void validateWithoutValue() {
        Categorie categorie = new Categorie();

        TestUtils.testValidation(categorie, Categorie.class, 1);
    }

    @Test
    void validateWithValue() {
        Categorie categorie = new Categorie();
        categorie.setNom("Test");

        assertDoesNotThrow(categorie::validate);
    }
}