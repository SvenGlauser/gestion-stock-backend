package ch.glauser.gestionstock.machine.controller;

import ch.glauser.gestionstock.GestionStockApplication;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.contact.controller.ContactController;
import ch.glauser.gestionstock.contact.dto.ContactDto;
import ch.glauser.gestionstock.contact.model.Titre;
import ch.glauser.gestionstock.machine.dto.MachineDto;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = GestionStockApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MachineControllerTest {

    @Autowired
    MachineController machineController;

    @Autowired
    ContactController contactController;

    @Test
    void get() {
        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setDescription("Machine - Description");
        machine.setNom("Machine");
        machine.setContact(this.getContact());

        machine = machineController.create(machine).getBody();

        assertThat(machine).isNotNull();

        MachineDto machineDto = machineController.get(machine.getId()).getBody();
        assertThat(machineDto).isNotNull();
        assertThat(machineDto.getNom())
                .isNotNull()
                .isEqualTo("Machine");
        assertThat(machineDto.getDescription())
                .isNotNull()
                .isEqualTo("Machine - Description");
        assertThat(machineDto.getContact())
                .isNotNull();
    }

    @Test
    void create() {
        ContactDto contact = this.getContact();

        // Test validation bien mise en place
        MachineDto machine = new MachineDto();
        TestUtils.testValidation(2, () -> machineController.create(machine));

        // Test cas OK
        machine.setNom("Machine");
        machine.setContact(contact);
        assertDoesNotThrow(() -> machineController.create(machine));

        MachineDto machine2 = new MachineDto();
        machine2.setNom("Machine");
        machine2.setContact(contact);

        // Test unicité du nom
        TestUtils.testValidation(1, () -> machineController.create(machine2));

        MachineDto machine3 = new MachineDto();
        machine3.setNom("Machine");
        machine3.setContact(this.getContact2());

        // Test unicité du nom
        assertDoesNotThrow(() -> machineController.create(machine3));

        MachineDto machine4 = new MachineDto();
        machine4.setNom("Machine 2");
        machine4.setContact(contact);

        // Test unicité du nom
        assertDoesNotThrow(() -> machineController.create(machine4));
    }

    @Test
    void search() {
        ContactDto contact = this.getContact();

        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setContact(contact);
        assertDoesNotThrow(() -> machineController.create(machine));

        MachineDto machine2 = new MachineDto();
        machine2.setNom("Machine 2");
        machine2.setContact(contact);
        assertDoesNotThrow(() -> machineController.create(machine2));

        MachineDto machine3 = new MachineDto();
        machine3.setNom("Machine");
        machine3.setContact(this.getContact2());
        assertDoesNotThrow(() -> machineController.create(machine3));

        SearchRequest searchRequest = new SearchRequest();
        
        SearchResult<MachineDto> result = machineController.search(searchRequest).getBody();
        assertThat(result).isNotNull();
        assertThat(result.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        Filter contactFilter = new Filter();
        contactFilter.setValue(contact.getId());
        contactFilter.setField("contact.id");
        SearchRequest searchRequest1 = new SearchRequest();
        searchRequest1.setFilter(List.of(contactFilter));
        SearchResult<MachineDto> result1 = machineController.search(searchRequest1).getBody();
        assertThat(result1).isNotNull();
        assertThat(result1.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Filter nom = new Filter();
        nom.setValue("Machine");
        nom.setField("nom");
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.setFilter(List.of(nom));
        SearchResult<MachineDto> result2 = machineController.search(searchRequest2).getBody();
        assertThat(result2).isNotNull();
        assertThat(result2.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void modify() {
        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setDescription("Machine - Description");
        machine.setContact(this.getContact());

        machine = machineController.create(machine).getBody();

        assertThat(machine).isNotNull();

        MachineDto machineDto = machineController.get(machine.getId()).getBody();
        assertThat(machineDto).isNotNull();
        assertThat(machineDto.getNom())
                .isNotNull()
                .isEqualTo("Machine");
        assertThat(machineDto.getDescription())
                .isNotNull()
                .isEqualTo("Machine - Description");
        assertThat(machineDto.getContact())
                .isNotNull();

        machine.setNom("Machine 2");
        machine.setDescription("Machine - Description 2");

        machineController.modify(machine).getBody();

        MachineDto machineDto2 = machineController.get(machine.getId()).getBody();
        assertThat(machineDto2).isNotNull();
        assertThat(machineDto2.getNom())
                .isNotNull()
                .isEqualTo("Machine 2");
        assertThat(machineDto2.getDescription())
                .isNotNull()
                .isEqualTo("Machine - Description 2");
        assertThat(machineDto2.getId()).isEqualTo(machineDto.getId());
    }

    @Test
    void modifyAvecNomUnique() {
        ContactDto contact1 = this.getContact();
        ContactDto contact2 = this.getContact2();

        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setContact(contact1);

        machine = machineController.create(machine).getBody();

        assertThat(machine).isNotNull();

        MachineDto machine2 = new MachineDto();
        machine2.setNom("Machine");
        machine2.setContact(contact2);

        machine2 = machineController.create(machine2).getBody();

        assertThat(machine2).isNotNull();

        MachineDto machine1SameName = machine;
        machine1SameName.setContact(contact2);
        TestUtils.testValidation(1, () -> machineController.modify(machine1SameName));
    }

    @Test
    void delete() {
        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setContact(this.getContact());

        machine = machineController.create(machine).getBody();

        assertThat(machine).isNotNull();

        machineController.delete(machine.getId());

        machine = machineController.get(machine.getId()).getBody();

        assertThat(machine).isNull();

        // Suppression inexistant
        TestUtils.testValidation(1, () -> machineController.delete(1000L));
    }

    private ContactDto getContact() {
        ContactDto contact = new ContactDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom");
        contact.setPrenom("Prenom");

        return contactController.create(contact).getBody();
    }

    private ContactDto getContact2() {
        ContactDto contact = new ContactDto();
        contact.setTitre(Titre.MONSIEUR.name());
        contact.setNom("Nom - 2");
        contact.setPrenom("Prenom - 2");

        return contactController.create(contact).getBody();
    }
}