package ch.glauser.gestionstock.machine.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.Validator;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des machines
 */
@RequiredArgsConstructor
public class MachineServiceImpl implements MachineService {

    public static final String FIELD_MACHINE = "machine";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final MachineRepository machineRepository;

    @Override
    public Machine getMachine(Long id) {
        Validator.of(MachineServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.machineRepository.getMachine(id);
    }

    @Override
    public SearchResult<Machine> searchMachine(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.machineRepository.searchMachine(searchRequest);
    }

    @Override
    public Machine createMachine(Machine machine) {
        Validator.of(MachineServiceImpl.class)
                .validateNotNull(machine, FIELD_MACHINE)
                .execute();

        machine.validate();

        return this.machineRepository.createMachine(machine);
    }

    @Override
    public Machine modifyMachine(Machine machine) {
        Validator.of(MachineServiceImpl.class)
                .validateNotNull(machine, FIELD_MACHINE)
                .execute();

        machine.validateModify();

        return this.machineRepository.modifyMachine(machine);
    }

    @Override
    public void deleteMachine(Long id) {
        Validator.of(MachineServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.machineRepository.deleteMachine(id);
    }
}
