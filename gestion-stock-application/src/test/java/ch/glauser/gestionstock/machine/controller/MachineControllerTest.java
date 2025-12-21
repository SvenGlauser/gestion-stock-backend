package ch.glauser.gestionstock.machine.controller;

import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.controller.PersonnePhysiqueController;
import ch.glauser.gestionstock.identite.dto.PersonnePhysiqueDto;
import ch.glauser.gestionstock.identite.model.Titre;
import ch.glauser.gestionstock.machine.dto.MachineDto;
import ch.glauser.gestionstock.utils.TestSecurityConfiguration;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = TestSecurityConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithUserDetails(TestSecurityConfiguration.TEST_ADMIN_USERNAME)
class MachineControllerTest {

    @Autowired
    MachineController machineController;

    @Autowired
    PersonnePhysiqueController personnePhysiqueController;

    @Test
    void get() {
        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setDescription("Machine - Description");
        machine.setProprietaire(this.getPersonnePhysique());

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
        assertThat(machineDto.getProprietaire())
                .isNotNull();
    }

    @Test
    void create() {
        PersonnePhysiqueDto contact = this.getPersonnePhysique();

        // Test validation bien mise en place
        MachineDto machine = new MachineDto();
        TestUtils.testValidation(2, () -> machineController.create(machine));

        // Test cas OK
        machine.setNom("Machine");
        machine.setProprietaire(contact);
        assertDoesNotThrow(() -> machineController.create(machine));

        MachineDto machine2 = new MachineDto();
        machine2.setNom("Machine");
        machine2.setProprietaire(contact);

        // Test unicité du nom
        TestUtils.testValidation(1, () -> machineController.create(machine2));

        MachineDto machine3 = new MachineDto();
        machine3.setNom("Machine");
        machine3.setProprietaire(this.getPersonnePhysique2());

        // Test unicité du nom
        assertDoesNotThrow(() -> machineController.create(machine3));

        MachineDto machine4 = new MachineDto();
        machine4.setNom("Machine 2");
        machine4.setProprietaire(contact);

        // Test unicité du nom
        assertDoesNotThrow(() -> machineController.create(machine4));
    }

    @Test
    void search() {
        PersonnePhysiqueDto contact = this.getPersonnePhysique();

        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setProprietaire(contact);
        assertDoesNotThrow(() -> machineController.create(machine));

        MachineDto machine2 = new MachineDto();
        machine2.setNom("Machine 2");
        machine2.setProprietaire(contact);
        assertDoesNotThrow(() -> machineController.create(machine2));

        MachineDto machine3 = new MachineDto();
        machine3.setNom("Machine");
        machine3.setProprietaire(this.getPersonnePhysique2());
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
        contactFilter.setField("proprietaire.id");
        SearchRequest searchRequest1 = new SearchRequest();
        searchRequest1.setCombinators(List.of(FilterCombinator.and(List.of(contactFilter))));
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
        searchRequest2.setCombinators(List.of(FilterCombinator.and(List.of(nom))));
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
        machine.setProprietaire(this.getPersonnePhysique());

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
        assertThat(machineDto.getProprietaire())
                .isNotNull();

        machine.setNom("Machine 2");
        machine.setDescription("Machine - Description 2");

        machineController.modify(machine);

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
        PersonnePhysiqueDto contact1 = this.getPersonnePhysique();
        PersonnePhysiqueDto contact2 = this.getPersonnePhysique2();

        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setProprietaire(contact1);

        machine = machineController.create(machine).getBody();

        assertThat(machine).isNotNull();

        MachineDto machine2 = new MachineDto();
        machine2.setNom("Machine");
        machine2.setProprietaire(contact2);

        machine2 = machineController.create(machine2).getBody();

        assertThat(machine2).isNotNull();

        MachineDto machine1SameName = machine;
        machine1SameName.setProprietaire(contact2);
        TestUtils.testValidation(1, () -> machineController.modify(machine1SameName));
    }

    @Test
    void delete() {
        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setProprietaire(this.getPersonnePhysique());

        machine = machineController.create(machine).getBody();

        assertThat(machine).isNotNull();

        machineController.delete(machine.getId());

        MachineDto finalMachine = machine;
        assertThatThrownBy(() -> machineController.get(finalMachine.getId()))
                .isInstanceOf(SearchWithInexistingIdExceptionPerform.class);

        // Suppression inexistant
        assertThatThrownBy(() -> machineController.delete(1000L))
                .isInstanceOf(DeleteWithInexistingIdException.class);
    }

    private PersonnePhysiqueDto getPersonnePhysique() {
        PersonnePhysiqueDto personnePhysiqueDto = new PersonnePhysiqueDto();
        personnePhysiqueDto.setTitre(Titre.MONSIEUR.name());
        personnePhysiqueDto.setNom("Nom");
        personnePhysiqueDto.setPrenom("Prenom");

        return personnePhysiqueController.create(personnePhysiqueDto).getBody();
    }

    private PersonnePhysiqueDto getPersonnePhysique2() {
        PersonnePhysiqueDto personnePhysiqueDto = new PersonnePhysiqueDto();
        personnePhysiqueDto.setTitre(Titre.MONSIEUR.name());
        personnePhysiqueDto.setNom("Nom - 2");
        personnePhysiqueDto.setPrenom("Prenom - 2");

        return personnePhysiqueController.create(personnePhysiqueDto).getBody();
    }
}