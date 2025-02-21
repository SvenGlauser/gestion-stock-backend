package ch.glauser.gestionstock.fournisseur.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.gestionstock.fournisseur.repository.FournisseurRepository;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des fournisseurs
 */
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {

    public static final String FIELD_FOURNISSEUR = "fournisseur";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";
    public static final String ERROR_SUPPRESSION_FOURNISSEUR_INEXISTANTE = "Impossible de supprimer ce fournisseur car il n'existe pas";
    public static final String ERROR_SUPPRESSION_FOURNISSEUR_IMPOSSIBLE_EXISTE_CONTACT = "Impossible de supprimer ce fournisseur car il existe une pièce liée";

    private final FournisseurRepository fournisseurRepository;

    private final PieceRepository pieceRepository;

    @Override
    public Fournisseur getFournisseur(Long id) {
        Validator.of(FournisseurServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.fournisseurRepository.getFournisseur(id);
    }

    @Override
    public SearchResult<Fournisseur> searchFournisseur(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.fournisseurRepository.searchFournisseur(searchRequest);
    }

    @Override
    public Fournisseur createFournisseur(Fournisseur fournisseur) {
        Validator.of(FournisseurServiceImpl.class)
                .validateNotNull(fournisseur, FIELD_FOURNISSEUR)
                .execute();

        fournisseur.validateCreate();

        return this.fournisseurRepository.createFournisseur(fournisseur);
    }

    @Override
    public Fournisseur modifyFournisseur(Fournisseur fournisseur) {
        Validator.of(FournisseurServiceImpl.class)
                .validateNotNull(fournisseur, FIELD_FOURNISSEUR)
                .execute();

        fournisseur.validateModify();

        return this.fournisseurRepository.modifyFournisseur(fournisseur);
    }

    @Override
    public void deleteFournisseur(Long id) {
        Validator.of(FournisseurServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.validateFournisseurExist(id);
        this.validatePasUtiliseParContact(id);

        this.fournisseurRepository.deleteFournisseur(id);
    }

    /**
     * Valide que le fournisseur existe
     * @param id Id du fournisseur à supprimer
     */
    private void validateFournisseurExist(Long id) {
        Fournisseur fournisseurToDelete = this.getFournisseur(id);

        if (Objects.isNull(fournisseurToDelete)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_FOURNISSEUR_INEXISTANTE,
                    FIELD_FOURNISSEUR,
                    FournisseurServiceImpl.class));
        }
    }

    /**
     * Valide que le fournisseur n'est pas utilisé par une pièce
     * @param id Id du fournisseur à supprimer
     */
    private void validatePasUtiliseParContact(Long id) {
        if (this.pieceRepository.existPieceWithIdFournisseur(id)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_FOURNISSEUR_IMPOSSIBLE_EXISTE_CONTACT,
                    FIELD_FOURNISSEUR,
                    FournisseurServiceImpl.class));
        }
    }
}
