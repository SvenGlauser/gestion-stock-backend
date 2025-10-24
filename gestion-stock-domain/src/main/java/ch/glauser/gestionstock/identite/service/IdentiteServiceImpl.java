package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.repository.FournisseurRepository;
import ch.glauser.gestionstock.identite.model.Identite;
import ch.glauser.gestionstock.identite.model.IdentiteConstantes;
import ch.glauser.gestionstock.identite.repository.IdentiteRepository;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import ch.glauser.validation.common.Error;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * Implémentation du service de gestion des identités
 */
@RequiredArgsConstructor
public class IdentiteServiceImpl implements IdentiteService {

    private final IdentiteRepository identiteRepository;
    private final MachineRepository machineRepository;
    private final FournisseurRepository fournisseurRepository;

    @Override
    public Set<Identite> findAllByDesignation(String designation) {
        return this.identiteRepository.findAllByDesignation(designation);
    }

    @Override
    public SearchResult<Identite> search(SearchRequest searchRequest) {
        Validation.of(IdentiteServiceImpl.class)
                .validateNotNull(searchRequest, IdentiteConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return identiteRepository.search(searchRequest);
    }

    @Override
    public void validateDelete(Long id) {
        Validation.of(IdentiteServiceImpl.class)
                .validateNotNull(id, IdentiteConstantes.FIELD_ID)
                .execute();

        this.validatePasUtiliseParMachine(id);
        this.validatePasUtiliseParFournisseur(id);
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
                    IdentiteServiceImpl.class));
        }
    }

    /**
     * Valide que l'identité n'est pas utilisée par une machine
     * @param id Id de l'identité à supprimer
     */
    private void validatePasUtiliseParFournisseur(Long id) {
        if (this.fournisseurRepository.existByIdIdentite(id)) {
            throw new ValidationException(new Error(
                    IdentiteConstantes.ERROR_SUPPRESSION_IDENTITE_IMPOSSIBLE_EXISTE_FOURNISSEUR,
                    IdentiteConstantes.FIELD_IDENTITE,
                    IdentiteServiceImpl.class));
        }
    }
}
