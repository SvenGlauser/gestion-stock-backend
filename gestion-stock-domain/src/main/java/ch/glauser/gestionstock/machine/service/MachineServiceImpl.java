package ch.glauser.gestionstock.machine.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import ch.glauser.gestionstock.pays.service.PaysServiceImpl;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des machines
 */
@RequiredArgsConstructor
public class MachineServiceImpl implements MachineService {

    public static final String FIELD_MACHINE = "machine";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";
    public static final String ERROR_SUPPRESSION_MACHINE_INEXISTANTE = "Impossible de supprimer cette machine car il n'existe pas";

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

        machine.validateCreate();

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

        this.validateMachineExist(id);

        this.machineRepository.deleteMachine(id);
    }

    /**
     * Valide que la machine existe
     * @param id Id de la machine à supprimer
     */
    private void validateMachineExist(Long id) {
        Machine machineToDelete = this.getMachine(id);

        if (Objects.isNull(machineToDelete)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_MACHINE_INEXISTANTE,
                    FIELD_MACHINE,
                    PaysServiceImpl.class));
        }
    }
}
