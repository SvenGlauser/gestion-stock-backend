package ch.glauser.gestionstock.pays.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import ch.glauser.gestionstock.localite.service.LocaliteServiceImpl;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.repository.PaysRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des payss
 */
@RequiredArgsConstructor
public class PaysServiceImpl implements PaysService {

    public static final String FIELD_PAYS = "pays";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";
    public static final String ERROR_SUPPRESSION_PAYS_INEXISTANTE = "Impossible de supprimer ce pays car il n'existe pas";
    public static final String ERROR_SUPPRESSION_PAYS_IMPOSSIBLE_EXISTE_LOCALITE = "Impossible de supprimer ce pays car il existe une localité l'utilisant";

    private final PaysRepository paysRepository;

    private final LocaliteRepository localiteRepository;

    @Override
    public Pays getPays(Long id) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.paysRepository.getPays(id);
    }

    @Override
    public SearchResult<Pays> searchPays(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.paysRepository.searchPays(searchRequest);
    }

    @Override
    public Pays createPays(Pays pays) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(pays, FIELD_PAYS)
                .execute();

        pays.validate();

        return this.paysRepository.createPays(pays);
    }

    @Override
    public Pays modifyPays(Pays pays) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(pays, FIELD_PAYS)
                .execute();

        pays.validateModify();

        return this.paysRepository.modifyPays(pays);
    }

    @Override
    public void deletePays(Long id) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.validatePaysExist(id);
        this.validatePasUtiliseParLocalite(id);

        this.paysRepository.deletePays(id);
    }

    /**
     * Valide que le pays existe
     * @param id Id du pays à supprimer
     */
    private void validatePaysExist(Long id) {
        Pays paysToDelete = this.getPays(id);

        if (Objects.isNull(paysToDelete)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_PAYS_INEXISTANTE,
                    FIELD_PAYS,
                    PaysServiceImpl.class));
        }
    }

    /**
     * Valide que la localité n'est pas utilisé par un fournisseur
     * @param id Id de la localité à supprimer
     */
    private void validatePasUtiliseParLocalite(Long id) {
        if (this.localiteRepository.existLocaliteWithIdPays(id)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_PAYS_IMPOSSIBLE_EXISTE_LOCALITE,
                    FIELD_PAYS,
                    LocaliteServiceImpl.class));
        }
    }
}
