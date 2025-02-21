package ch.glauser.gestionstock.localite.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.contact.service.ContactService;
import ch.glauser.gestionstock.fournisseur.service.FournisseurService;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des localités
 */
@RequiredArgsConstructor
public class LocaliteServiceImpl implements LocaliteService {

    public static final String FIELD_LOCALITE = "localite";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";
    public static final String ERROR_SUPPRESSION_LOCALITE_INEXISTANTE = "Impossible de supprimer cette localité car elle n'existe pas";
    public static final String ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_CONTACT = "Impossible de supprimer cette localité car il existe un contact l'utilisant";
    public static final String ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_FOURNISSEUR = "Impossible de supprimer cette localité car il existe un fournisseur l'utilisant";

    private final LocaliteRepository localiteRepository;

    private final ContactService contactService;
    private final FournisseurService fournisseurService;

    @Override
    public Localite getLocalite(Long id) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.localiteRepository.getLocalite(id);
    }

    @Override
    public SearchResult<Localite> searchLocalite(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.localiteRepository.searchLocalite(searchRequest);
    }

    @Override
    public Localite createLocalite(Localite localite) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(localite, FIELD_LOCALITE)
                .execute();

        localite.validate();

        return this.localiteRepository.createLocalite(localite);
    }

    @Override
    public Localite modifyLocalite(Localite localite) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(localite, FIELD_LOCALITE)
                .execute();

        localite.validateModify();

        return this.localiteRepository.modifyLocalite(localite);
    }

    @Override
    public void deleteLocalite(Long id) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
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
                    ERROR_SUPPRESSION_LOCALITE_INEXISTANTE,
                    FIELD_LOCALITE,
                    LocaliteServiceImpl.class));
        }
    }

    /**
     * Valide que la localité n'est pas utilisé par un contact
     * @param id Id de la localité à supprimer
     */
    private void validatePasUtiliseParContact(Long id) {
        if (this.contactService.existContactWithIdLocalite(id)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_CONTACT,
                    FIELD_LOCALITE,
                    LocaliteServiceImpl.class));
        }
    }

    /**
     * Valide que la localité n'est pas utilisé par un fournisseur
     * @param id Id de la localité à supprimer
     */
    private void validatePasUtiliseParFournisseur(Long id) {
        if (this.fournisseurService.existFournisseurWithIdLocalite(id)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_LOCALITE_IMPOSSIBLE_EXISTE_FOURNISSEUR,
                    FIELD_LOCALITE,
                    LocaliteServiceImpl.class));
        }
    }
}
