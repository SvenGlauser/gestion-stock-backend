package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.identite.model.Identite;
import ch.glauser.gestionstock.identite.model.IdentiteConstantes;
import ch.glauser.gestionstock.identite.repository.IdentiteRepository;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des identités
 */
@RequiredArgsConstructor
public class IdentiteServiceImpl implements IdentiteService {

    private final IdentiteRepository identiteRepository;
    private final MachineRepository machineRepository;

    @Override
    public SearchResult<Identite> search(SearchRequest searchRequest) {
        Validation.of(PersonnePhysiqueServiceImpl.class)
                .validateNotNull(searchRequest, IdentiteConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return identiteRepository.search(searchRequest);
    }

    @Override
    public void validateDelete(Long id) {
        Validation.of(PersonnePhysiqueServiceImpl.class)
                .validateNotNull(id, IdentiteConstantes.FIELD_ID)
                .execute();

        this.validatePasUtiliseParMachine(id);
    }

    /**
     * Valide que l'identité n'est pas utilisée par une machine
     * @param id Id de l'identité à supprimer
     */
    private void validatePasUtiliseParMachine(Long id) {
        if (this.machineRepository.existByIdProprietaire(id)) {
            throw new ValidationException(new Error(
                    IdentiteConstantes.ERROR_SUPPRESSION_IDENTITE_IMPOSSIBLE_EXISTE_MACHINE,
                    IdentiteConstantes.FIELD_IDENTITE,
                    PersonnePhysiqueServiceImpl.class));
        }
    }
}
