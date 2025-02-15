package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.contact.model.Contact;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MachineTest {
    @Test
    void validateWithoutValue() {
        Machine machine = new Machine();

        TestUtils.testValidation(machine, Machine.class, 2);
    }

    @Test
    void validateWithValue() {
        Machine machine = new Machine();
        machine.setNom("Test");
        machine.setContact(new Contact());

        assertDoesNotThrow(machine::validate);
    }
}