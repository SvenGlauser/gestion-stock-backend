package ch.glauser.gestionstock.pays.model;

import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PaysTest {
    @Test
    void validateWithoutValue() {
        Pays pays = new Pays();

        TestUtils.testValidation(pays, Pays.class, 2);
    }

    @Test
    void validateWithValue() {
        Pays pays = new Pays();
        pays.setNom("Suisse");
        pays.setAbbreviation("CH");

        assertDoesNotThrow(pays::validate);
    }
}