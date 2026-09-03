package ch.glauser.gestionstock.machine.controller;

import ch.glauser.filters.automatic.AutomaticSearchField;
import ch.glauser.filters.automatic.AutomaticSearchFieldCombinaison;
import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.controller.PersonnePhysiqueController;
import ch.glauser.gestionstock.identite.dto.PersonnePhysiqueDto;
import ch.glauser.gestionstock.identite.model.Titre;
import ch.glauser.gestionstock.machine.dto.MachineDto;
import ch.glauser.gestionstock.machine.dto.ServiceDto;
import ch.glauser.gestionstock.machine.model.Service;
import ch.glauser.gestionstock.utils.TestSecurityConfiguration;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = TestSecurityConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithUserDetails(TestSecurityConfiguration.TEST_ADMIN_USERNAME)
class ServiceControllerTest {

    @Autowired
    ServiceController serviceController;

    @Autowired
    MachineController machineController;

    @Autowired
    PersonnePhysiqueController personnePhysiqueController;

    @Test
    void get() {
        ServiceDto service = new ServiceDto();
        service.setMachine(this.getMachine());
        service.setDate(LocalDate.now());
        service.setDuree(BigDecimal.ONE);

        service = serviceController.create(service).getBody();

        assertThat(service).isNotNull();

        ServiceDto serviceDto = serviceController.get(service.getId()).getBody();
        assertThat(serviceDto).isNotNull();
        assertThat(serviceDto.getMachine().getId())
                .isNotNull()
                .isEqualTo(service.getMachine().getId());
        assertThat(serviceDto.getDate())
                .isNotNull()
                .isEqualTo(service.getDate());
        assertThat(serviceDto.getDuree())
                .isNotNull()
                .isEqualByComparingTo(service.getDuree());
    }

    @Test
    void create() {
        // Test validation bien mise en place
        ServiceDto service = new ServiceDto();
        TestUtils.testValidation(3, () -> serviceController.create(service));

        // Test cas OK
        service.setMachine(this.getMachine());
        service.setDate(LocalDate.now());
        service.setDuree(BigDecimal.ONE);
        assertDoesNotThrow(() -> serviceController.create(service));
    }

    @Test
    void search() {
        LocalDate now = LocalDate.now();
        MachineDto machine = this.getMachine();

        ServiceDto service = new ServiceDto();
        service.setMachine(machine);
        service.setDate(now);
        service.setDuree(BigDecimal.ONE);
        assertDoesNotThrow(() -> serviceController.create(service));

        ServiceDto service2 = new ServiceDto();
        service2.setMachine(machine);
        service2.setDate(now);
        service2.setDuree(BigDecimal.TWO);
        assertDoesNotThrow(() -> serviceController.create(service2));

        ServiceDto service3 = new ServiceDto();
        service3.setMachine(machine);
        service3.setDate(now.minusDays(1));
        service3.setDuree(BigDecimal.TWO);
        assertDoesNotThrow(() -> serviceController.create(service3));

        AutomaticSearchQuery automaticSearchQuery = new AutomaticSearchQuery();
        
        SearchResult<ServiceDto> result = serviceController.search(automaticSearchQuery).getBody();
        assertThat(result).isNotNull();
        assertThat(result.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        AutomaticSearchField<Long> contactFilter = new AutomaticSearchField<>();
        contactFilter.setValue(machine.getId());
        contactFilter.setField("machine.id");
        AutomaticSearchQuery automaticSearchQuery1 = new AutomaticSearchQuery();
        automaticSearchQuery1.setCombinators(List.of(AutomaticSearchFieldCombinaison.and(List.of(contactFilter))));
        SearchResult<ServiceDto> result1 = serviceController.search(automaticSearchQuery1).getBody();
        assertThat(result1).isNotNull();
        assertThat(result1.getElements())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    void modify() {
        ServiceDto service = new ServiceDto();
        service.setMachine(this.getMachine());
        service.setDate(LocalDate.now());
        service.setDuree(BigDecimal.ONE);

        service = serviceController.create(service).getBody();

        assertThat(service).isNotNull();

        ServiceDto serviceDto = serviceController.get(service.getId()).getBody();
        assertThat(serviceDto).isNotNull();
        assertThat(serviceDto.getMachine().getId())
                .isNotNull()
                .isEqualTo(service.getMachine().getId());
        assertThat(serviceDto.getDate())
                .isNotNull()
                .isEqualTo(service.getDate());
        assertThat(serviceDto.getDuree())
                .isNotNull()
                .isEqualByComparingTo(service.getDuree());

        serviceDto.setDate(LocalDate.now().minusDays(10));
        serviceDto.setDescriptionTravaux("Description 1");
        serviceDto.setDivers("Divers 1");

        serviceController.modify(serviceDto);

        ServiceDto serviceDto2 = serviceController.get(service.getId()).getBody();
        assertThat(serviceDto2).isNotNull();
        assertThat(serviceDto2.getDescriptionTravaux())
                .isNotNull()
                .isEqualTo("Description 1");
        assertThat(serviceDto2.getDivers())
                .isNotNull()
                .isEqualTo("Divers 1");
        assertThat(serviceDto2.getDate())
                .isNotNull()
                .isEqualTo(serviceDto.getDate());
        assertThat(serviceDto2.getId()).isEqualTo(serviceDto.getId());
    }

    @Test
    void delete() {
        ServiceDto service = new ServiceDto();
        service.setMachine(this.getMachine());
        service.setDate(LocalDate.now());
        service.setDuree(BigDecimal.ONE);

        service = serviceController.create(service).getBody();

        assertThat(service).isNotNull();

        serviceController.delete(service.getId());

        Long serviceId = service.getId();
        assertThatException()
                .isThrownBy(() -> serviceController.get(serviceId))
                .isInstanceOf(SearchWithInexistingIdExceptionPerform.class);

        // Suppression inexistant
        assertThatException()
                .isThrownBy(() -> serviceController.delete(1000L))
                .isInstanceOf(DeleteWithInexistingIdException.class);
    }

    private MachineDto getMachine() {
        PersonnePhysiqueDto contact = this.getPersonnePhysique();

        MachineDto machine = new MachineDto();
        machine.setNom("Machine");
        machine.setProprietaire(contact);

        return machineController.create(machine).getBody();
    }

    private PersonnePhysiqueDto getPersonnePhysique() {
        PersonnePhysiqueDto personnePhysiqueDto = new PersonnePhysiqueDto();
        personnePhysiqueDto.setTitre(Titre.MONSIEUR.name());
        personnePhysiqueDto.setNom("Nom");
        personnePhysiqueDto.setPrenom("Prenom");

        return personnePhysiqueController.create(personnePhysiqueDto).getBody();
    }
}