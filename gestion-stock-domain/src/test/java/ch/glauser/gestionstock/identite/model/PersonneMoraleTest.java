package ch.glauser.gestionstock.identite.model;

import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PersonneMoraleTest {

    @Test
    void validateWithoutValue() {
        PersonneMorale personneMorale = new PersonneMorale();

        TestUtils.testValidation(personneMorale, PersonneMorale.class, 1);
    }

    @Test
    void validateWithValue() {
        PersonneMorale personneMorale = new PersonneMorale();
        personneMorale.setRaisonSociale("Entreprise SA");

        assertDoesNotThrow(personneMorale::validateCreate);
    }
}