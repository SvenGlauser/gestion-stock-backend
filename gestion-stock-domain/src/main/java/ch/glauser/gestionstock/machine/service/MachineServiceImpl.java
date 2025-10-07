package ch.glauser.gestionstock.machine.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.ModifyWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.PerformActionWithInexistingIdFunction;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.gestionstock.machine.model.MachineConstantes;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import ch.glauser.gestionstock.validation.common.Validation;
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
    public Machine get(Long id) {
        Validation.of(MachineServiceImpl.class)
                .validateNotNull(id, MachineConstantes.FIELD_ID)
                .execute();

        return this.machineRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, Machine.class));
    }

    @Override
    public SearchResult<Machine> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, MachineConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.machineRepository.search(searchRequest);
    }

    @Override
    public Machine create(Machine machine) {
        Validation.of(MachineServiceImpl.class)
                .validateNotNull(machine, MachineConstantes.FIELD_MACHINE)
                .execute();

        Validation validation = machine.validateCreate();

        Long idProprietaire = Optional.ofNullable(machine.getProprietaire()).map(Model::getId).orElse(null);

        if (this.machineRepository.existByNomAndIdProprietaire(machine.getNom(), idProprietaire)) {
            validation.addError(MachineConstantes.ERROR_MACHINE_NOM_UNIQUE, MachineConstantes.FIELD_NOM);
        }

        validation.execute();

        return this.machineRepository.create(machine);
    }

    @Override
    public Machine modify(Machine machine) {
        Validation.of(MachineServiceImpl.class)
                .validateNotNull(machine, MachineConstantes.FIELD_MACHINE)
                .execute();

        Machine oldMachine = this.machineRepository
                .get(machine.getId())
                .orElseThrow(() -> new ModifyWithInexistingIdException(machine.getId(), Machine.class));

        Validation validation = machine.validateModify();

        Long idProprietaire = Optional.ofNullable(machine.getProprietaire()).map(Model::getId).orElse(null);

        if ((
                !Objects.equals(oldMachine.getNom(), machine.getNom()) ||
                !Objects.equals(oldMachine.getProprietaire().getId(), idProprietaire)
            ) && this.machineRepository.existByNomAndIdProprietaire(machine.getNom(), idProprietaire)) {
            validation.addError(MachineConstantes.ERROR_MACHINE_NOM_UNIQUE, MachineConstantes.FIELD_NOM);
        }

        validation.execute();

        return this.machineRepository.modify(machine);
    }

    @Override
    public void delete(Long id) {
        Validation.of(MachineServiceImpl.class)
                .validateNotNull(id, MachineConstantes.FIELD_ID)
                .execute();

        this.validateExist(id, DeleteWithInexistingIdException::new);

        this.machineRepository.delete(id);
    }

    /**
     * Valide que la machine existe
     * @param id Id de la machine à supprimer
     * @param exception Exception
     */
    private void validateExist(Long id, PerformActionWithInexistingIdFunction exception) {
        this.machineRepository
                .get(id)
                .orElseThrow(() -> exception.instantiate(id, Machine.class));
    }
}
