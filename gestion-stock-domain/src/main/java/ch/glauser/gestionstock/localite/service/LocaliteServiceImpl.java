package ch.glauser.gestionstock.localite.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.common.validation.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.validation.exception.id.ModifyWithInexistingIdException;
import ch.glauser.gestionstock.common.validation.exception.id.PerformActionWithInexistingIdFunction;
import ch.glauser.gestionstock.common.validation.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.fournisseur.repository.FournisseurRepository;
import ch.glauser.gestionstock.identite.repository.IdentiteRepository;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.localite.model.LocaliteConstantes;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

/**
 * Implémentation du service de gestion des localités
 */
@RequiredArgsConstructor
public class LocaliteServiceImpl implements LocaliteService {

    private final LocaliteRepository localiteRepository;

    private final IdentiteRepository identiteRepository;
    private final FournisseurRepository fournisseurRepository;

    @Override
    public Localite get(Long id) {
        Validation.of(LocaliteServiceImpl.class)
                .validateNotNull(id, LocaliteConstantes.FIELD_ID)
                .execute();

        return this.localiteRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, Localite.class));
    }

    @Override
    public SearchResult<Localite> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, LocaliteConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.localiteRepository.search(searchRequest);
    }

    @Override
    public Localite create(Localite localite) {
        Validation.of(LocaliteServiceImpl.class)
                .validateNotNull(localite, LocaliteConstantes.FIELD_LOCALITE)
                .execute();

        Validation validation = localite.validateCreate();

        Long idPays = Optional.ofNullable(localite.getPays()).map(Model::getId).orElse(null);

        if (this.localiteRepository.existByNpaAndNomAndIdPays(localite.getNpa(), localite.getNom(), idPays)) {
            validation.addError(LocaliteConstantes.ERROR_LOCALITE_NOM_UNIQUE, LocaliteConstantes.FIELD_NOM);
        }

        validation.execute();

        return this.localiteRepository.create(localite);
    }

    @Override
    public Localite modify(Localite localite) {
        Validation.of(LocaliteServiceImpl.class)
                .validateNotNull(localite, LocaliteConstantes.FIELD_LOCALITE)
                .execute();

        Localite oldLocalite = this.localiteRepository
                .get(localite.getId())
                .orElseThrow(() -> new ModifyWithInexistingIdException(localite.getId(), Localite.class));

        Validation validation = localite.validateModify();

        if (Objects.nonNull(oldLocalite)) {
            Long idPays = Optional.ofNullable(localite.getPays()).map(Model::getId).orElse(null);

            if ((
                    !Objects.equals(oldLocalite.getNom(), localite.getNom()) ||
                    !Objects.equals(oldLocalite.getPays().getId(), idPays)
                ) && this.localiteRepository.existByNpaAndNomAndIdPays(localite.getNpa(), localite.getNom(), idPays)) {
                validation.addError(LocaliteConstantes.ERROR_LOCALITE_NOM_UNIQUE, LocaliteConstantes.FIELD_NOM);
            }
        }

        validation.execute();

        return this.localiteRepository.modify(localite);
    }

    @Override
    public void delete(Long id) {
        Validation.of(LocaliteServiceImpl.class)
                .validateNotNull(id, LocaliteConstantes.FIELD_ID)
                .execute();

        this.validateExist(id, DeleteWithInexistingIdException::new);
        this.validatePasUtiliseParIdentite(id);
        this.validatePasUtiliseParFournisseur(id);

        this.localiteRepository.delete(id);
    }

    /**
     * Valide que la localité existe
     * @param id Id de la localité à supprimer
     * @param exception Exception
     */
    private void validateExist(Long id, PerformActionWithInexistingIdFunction exception) {
        this.localiteRepository
                .get(id)
                .orElseThrow(() -> exception.instantiate(id, Localite.class));
    }

    /**
     * Valide que la localité n'est pas utilisé par une identité
     * @param id Id de la localité à supprimer
     */
    private void validatePasUtiliseParIdentite(Long id) {
        if (this.identiteRepository.existByIdLocalite(id)) {
            throw new ValidationException(new Error(
                    LocaliteConstantes.ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_IDENTITE,
                    LocaliteConstantes.FIELD_LOCALITE,
                    LocaliteServiceImpl.class));
        }
    }

    /**
     * Valide que la localité n'est pas utilisé par un fournisseur
     * @param id Id de la localité à supprimer
     */
    private void validatePasUtiliseParFournisseur(Long id) {
        if (this.fournisseurRepository.existByIdLocalite(id)) {
            throw new ValidationException(new Error(
                    LocaliteConstantes.ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_FOURNISSEUR,
                    LocaliteConstantes.FIELD_LOCALITE,
                    LocaliteServiceImpl.class));
        }
    }
}
