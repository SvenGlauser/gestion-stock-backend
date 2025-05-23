package ch.glauser.gestionstock.identite.model;

import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PersonnePhysiqueTest {

    @Test
    void validateWithoutValue() {
        PersonnePhysique personnePhysique = new PersonnePhysique();

        TestUtils.testValidation(personnePhysique, PersonnePhysique.class, 3);
    }

    @Test
    void validateWithValue() {
        PersonnePhysique personnePhysique = new PersonnePhysique();
        personnePhysique.setTitre(Titre.MONSIEUR);
        personnePhysique.setNom("Test");
        personnePhysique.setPrenom("Test");

        assertDoesNotThrow(personnePhysique::validateCreate);
    }
}