package ch.glauser.gestionstock.localite.model;

import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LocaliteTest {
    @Test
    void validateWithoutValue() {
        Localite localite = new Localite();

        TestUtils.testValidation(localite, Localite.class, 3);
    }

    @Test
    void validateWithValue() {
        Localite localite = new Localite();
        localite.setNom("Test");
        localite.setNpa("Test");
        localite.setPays(new Pays());

        assertDoesNotThrow(localite::validateCreate);
    }
}