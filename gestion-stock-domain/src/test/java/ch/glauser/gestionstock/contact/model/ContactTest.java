package ch.glauser.gestionstock.contact.model;

import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ContactTest {

    @Test
    void validateWithoutValue() {
        Contact contact = new Contact();

        TestUtils.testValidation(contact, Contact.class, 3);
    }

    @Test
    void validateWithValue() {
        Contact contact = new Contact();
        contact.setTitre(Titre.MONSIEUR);
        contact.setNom("Test");
        contact.setPrenom("Test");

        assertDoesNotThrow(contact::validate);
    }
}