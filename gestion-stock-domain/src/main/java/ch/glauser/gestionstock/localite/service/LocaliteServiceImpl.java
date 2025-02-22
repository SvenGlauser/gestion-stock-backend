package ch.glauser.gestionstock.localite.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.contact.repository.ContactRepository;
import ch.glauser.gestionstock.fournisseur.repository.FournisseurRepository;
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

    private final ContactRepository contactRepository;
    private final FournisseurRepository fournisseurRepository;

    @Override
    public Localite getLocalite(Long id) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(id, LocaliteConstantes.FIELD_ID)
                .execute();

        return this.localiteRepository.getLocalite(id);
    }

    @Override
    public SearchResult<Localite> searchLocalite(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, LocaliteConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.localiteRepository.searchLocalite(searchRequest);
    }

    @Override
    public Localite createLocalite(Localite localite) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(localite, LocaliteConstantes.FIELD_LOCALITE)
                .execute();

        Validator validator = localite.validateCreate();

        Long idPays = Optional.ofNullable(localite.getPays()).map(Model::getId).orElse(null);

        if (this.localiteRepository.existLocaliteByNpaAndNomAndIdPays(localite.getNpa(), localite.getNom(), idPays)) {
            validator.addError(LocaliteConstantes.ERROR_LOCALITE_NOM_UNIQUE, LocaliteConstantes.FIELD_NOM);
        }

        validator.execute();

        return this.localiteRepository.createLocalite(localite);
    }

    @Override
    public Localite modifyLocalite(Localite localite) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(localite, LocaliteConstantes.FIELD_LOCALITE)
                .execute();

        Localite oldLocalite = this.localiteRepository.getLocalite(localite.getId());

        Validator validator = localite.validateModify();

        if (Objects.nonNull(oldLocalite)) {
            Long idPays = Optional.ofNullable(localite.getPays()).map(Model::getId).orElse(null);

            if ((
                    !Objects.equals(oldLocalite.getNom(), localite.getNom()) ||
                    !Objects.equals(oldLocalite.getPays().getId(), idPays)
                ) && this.localiteRepository.existLocaliteByNpaAndNomAndIdPays(localite.getNpa(), localite.getNom(), idPays)) {
                validator.addError(LocaliteConstantes.ERROR_LOCALITE_NOM_UNIQUE, LocaliteConstantes.FIELD_NOM);
            }
        }

        validator.execute();

        return this.localiteRepository.modifyLocalite(localite);
    }

    @Override
    public void deleteLocalite(Long id) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(id, LocaliteConstantes.FIELD_ID)
                .execute();

        this.validateLocaliteExist(id);
        this.validatePasUtiliseParContact(id);
        this.validatePasUtiliseParFournisseur(id);

        this.localiteRepository.deleteLocalite(id);
    }

    /**
     * Valide que la localité existe
     * @param id Id de la localité à supprimer
     */
    private void validateLocaliteExist(Long id) {
        Localite localiteToDelete = this.getLocalite(id);

        if (Objects.isNull(localiteToDelete)) {
            throw new ValidationException(new Error(
                    LocaliteConstantes.ERROR_SUPPRESSION_LOCALITE_INEXISTANTE,
                    LocaliteConstantes.FIELD_LOCALITE,
                    LocaliteServiceImpl.class));
        }
    }

    /**
     * Valide que la localité n'est pas utilisé par un contact
     * @param id Id de la localité à supprimer
     */
    private void validatePasUtiliseParContact(Long id) {
        if (this.contactRepository.existContactByIdLocalite(id)) {
            throw new ValidationException(new Error(
                    LocaliteConstantes.ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_CONTACT,
                    LocaliteConstantes.FIELD_LOCALITE,
                    LocaliteServiceImpl.class));
        }
    }

    /**
     * Valide que la localité n'est pas utilisé par un fournisseur
     * @param id Id de la localité à supprimer
     */
    private void validatePasUtiliseParFournisseur(Long id) {
        if (this.fournisseurRepository.existFournisseurByIdLocalite(id)) {
            throw new ValidationException(new Error(
                    LocaliteConstantes.ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_FOURNISSEUR,
                    LocaliteConstantes.FIELD_LOCALITE,
                    LocaliteServiceImpl.class));
        }
    }
}
