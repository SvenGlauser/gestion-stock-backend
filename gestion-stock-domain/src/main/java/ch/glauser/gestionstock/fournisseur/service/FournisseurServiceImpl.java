package ch.glauser.gestionstock.fournisseur.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.gestionstock.fournisseur.model.FournisseurConstantes;
import ch.glauser.gestionstock.fournisseur.repository.FournisseurRepository;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des fournisseurs
 */
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    private final PieceRepository pieceRepository;

    @Override
    public Fournisseur getFournisseur(Long id) {
        Validation.of(FournisseurServiceImpl.class)
                .validateNotNull(id, FournisseurConstantes.FIELD_ID)
                .execute();

        return this.fournisseurRepository.getFournisseur(id);
    }

    @Override
    public SearchResult<Fournisseur> searchFournisseur(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FournisseurConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.fournisseurRepository.searchFournisseur(searchRequest);
    }

    @Override
    public Fournisseur createFournisseur(Fournisseur fournisseur) {
        Validation.of(FournisseurServiceImpl.class)
                .validateNotNull(fournisseur, FournisseurConstantes.FIELD_FOURNISSEUR)
                .execute();

        Validation validation = fournisseur.validateCreate();

        if (this.fournisseurRepository.existFournisseurByNom(fournisseur.getNom())) {
            validation.addError(FournisseurConstantes.ERROR_FOURNISSEUR_NOM_UNIQUE, FournisseurConstantes.FIELD_NOM);
        }

        validation.execute();

        return this.fournisseurRepository.createFournisseur(fournisseur);
    }

    @Override
    public Fournisseur modifyFournisseur(Fournisseur fournisseur) {
        Validation.of(FournisseurServiceImpl.class)
                .validateNotNull(fournisseur, FournisseurConstantes.FIELD_FOURNISSEUR)
                .execute();

        Fournisseur oldFournisseur = this.fournisseurRepository.getFournisseur(fournisseur.getId());

        Validation validation = fournisseur.validateModify();

        if (Objects.nonNull(oldFournisseur) &&
            !Objects.equals(oldFournisseur.getNom(), fournisseur.getNom()) &&
            this.fournisseurRepository.existFournisseurByNom(fournisseur.getNom())) {

            // Valide le cas dans lequel la catégorie a changé de nom
            validation.addError(FournisseurConstantes.ERROR_FOURNISSEUR_NOM_UNIQUE, FournisseurConstantes.FIELD_NOM);
        }

        validation.execute();

        return this.fournisseurRepository.modifyFournisseur(fournisseur);
    }

    @Override
    public void deleteFournisseur(Long id) {
        Validation.of(FournisseurServiceImpl.class)
                .validateNotNull(id, FournisseurConstantes.FIELD_ID)
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
                    FournisseurConstantes.ERROR_SUPPRESSION_FOURNISSEUR_INEXISTANTE,
                    FournisseurConstantes.FIELD_FOURNISSEUR,
                    FournisseurServiceImpl.class));
        }
    }

    /**
     * Valide que le fournisseur n'est pas utilisé par une pièce
     * @param id Id du fournisseur à supprimer
     */
    private void validatePasUtiliseParContact(Long id) {
        if (this.pieceRepository.existPieceByIdFournisseur(id)) {
            throw new ValidationException(new Error(
                    FournisseurConstantes.ERROR_SUPPRESSION_FOURNISSEUR_IMPOSSIBLE_EXISTE_CONTACT,
                    FournisseurConstantes.FIELD_FOURNISSEUR,
                    FournisseurServiceImpl.class));
        }
    }
}
