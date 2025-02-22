package ch.glauser.gestionstock.machine.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.gestionstock.machine.model.MachineConstantes;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import ch.glauser.gestionstock.pays.service.PaysServiceImpl;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

/**
 * Implémentation du service de gestion des machines
 */
@RequiredArgsConstructor
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;

    @Override
    public Machine getMachine(Long id) {
        Validator.of(MachineServiceImpl.class)
                .validateNotNull(id, MachineConstantes.FIELD_ID)
                .execute();

        return this.machineRepository.getMachine(id);
    }

    @Override
    public SearchResult<Machine> searchMachine(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, MachineConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.machineRepository.searchMachine(searchRequest);
    }

    @Override
    public Machine createMachine(Machine machine) {
        Validator.of(MachineServiceImpl.class)
                .validateNotNull(machine, MachineConstantes.FIELD_MACHINE)
                .execute();

        Validator validator = machine.validateCreate();

        Long idContact = Optional.ofNullable(machine.getContact()).map(Model::getId).orElse(null);

        if (this.machineRepository.existMachineByNomAndIdContact(machine.getNom(), idContact)) {
            validator.addError(MachineConstantes.ERROR_MACHINE_NOM_UNIQUE, MachineConstantes.FIELD_NOM);
        }

        validator.execute();

        return this.machineRepository.createMachine(machine);
    }

    @Override
    public Machine modifyMachine(Machine machine) {
        Validator.of(MachineServiceImpl.class)
                .validateNotNull(machine, MachineConstantes.FIELD_MACHINE)
                .execute();

        Machine oldMachine = this.machineRepository.getMachine(machine.getId());

        Validator validator = machine.validateModify();

        if (Objects.nonNull(oldMachine)) {
            Long idContact = Optional.ofNullable(machine.getContact()).map(Model::getId).orElse(null);

            if ((
                    !Objects.equals(oldMachine.getNom(), machine.getNom()) ||
                    !Objects.equals(oldMachine.getContact().getId(), idContact)
                ) && this.machineRepository.existMachineByNomAndIdContact(machine.getNom(), idContact)) {
                validator.addError(MachineConstantes.ERROR_MACHINE_NOM_UNIQUE, MachineConstantes.FIELD_NOM);
            }
        }

        validator.execute();

        return this.machineRepository.modifyMachine(machine);
    }

    @Override
    public void deleteMachine(Long id) {
        Validator.of(MachineServiceImpl.class)
                .validateNotNull(id, MachineConstantes.FIELD_ID)
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
                    MachineConstantes.ERROR_SUPPRESSION_MACHINE_INEXISTANTE,
                    MachineConstantes.FIELD_MACHINE,
                    PaysServiceImpl.class));
        }
    }
}
