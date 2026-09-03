package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ServiceTest {
    @Test
    void validateWithoutValue() {
        Service service = new Service();

        TestUtils.testValidation(service, Service.class, 3);

        Service serviceWithPieceVide = new Service();
        serviceWithPieceVide.setChangementsPieces(Set.of(new ChangementPiece()));

        TestUtils.testValidation(serviceWithPieceVide, Service.class, 6);
    }

    @Test
    void validateWithValue() {
        Machine machine = new Machine();
        machine.setNom("Test");
        machine.setProprietaire(new PersonnePhysique());

        Service service = new Service();
        service.setMachine(machine);
        service.setDuree(BigDecimal.ONE);
        service.setDate(LocalDate.now());

        assertDoesNotThrow(service::validateCreate);
    }
}