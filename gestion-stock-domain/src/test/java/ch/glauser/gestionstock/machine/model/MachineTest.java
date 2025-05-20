package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.identite.model.PersonnePhysique;
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
        machine.setProprietaire(new PersonnePhysique());

        assertDoesNotThrow(machine::validateCreate);
    }
}