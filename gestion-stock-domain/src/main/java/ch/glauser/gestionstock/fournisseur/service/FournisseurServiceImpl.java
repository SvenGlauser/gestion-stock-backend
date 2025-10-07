package ch.glauser.gestionstock.fournisseur.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.ModifyWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.PerformActionWithInexistingIdFunction;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.gestionstock.fournisseur.model.FournisseurConstantes;
import ch.glauser.gestionstock.fournisseur.repository.FournisseurRepository;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import ch.glauser.gestionstock.validation.common.Error;
import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.exception.ValidationException;
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
    public Fournisseur get(Long id) {
        Validation.of(FournisseurServiceImpl.class)
                .validateNotNull(id, FournisseurConstantes.FIELD_ID)
                .execute();

        return this.fournisseurRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, Fournisseur.class));
    }

    @Override
    public SearchResult<Fournisseur> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FournisseurConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.fournisseurRepository.search(searchRequest);
    }

    @Override
    public Fournisseur create(Fournisseur fournisseur) {
        Validation.of(FournisseurServiceImpl.class)
                .validateNotNull(fournisseur, FournisseurConstantes.FIELD_FOURNISSEUR)
                .execute();

        Validation validation = fournisseur.validateCreate();

        if (this.fournisseurRepository.existByNom(fournisseur.getNom())) {
            validation.addError(FournisseurConstantes.ERROR_FOURNISSEUR_NOM_UNIQUE, FournisseurConstantes.FIELD_NOM);
        }

        validation.execute();

        return this.fournisseurRepository.create(fournisseur);
    }

    @Override
    public Fournisseur modify(Fournisseur fournisseur) {
        Validation.of(FournisseurServiceImpl.class)
                .validateNotNull(fournisseur, FournisseurConstantes.FIELD_FOURNISSEUR)
                .execute();

        Fournisseur oldFournisseur = this.fournisseurRepository
                .get(fournisseur.getId())
                .orElseThrow(() -> new ModifyWithInexistingIdException(fournisseur.getId(), Fournisseur.class));

        Validation validation = fournisseur.validateModify();

        if (!Objects.equals(oldFournisseur.getNom(), fournisseur.getNom()) &&
            this.fournisseurRepository.existByNom(fournisseur.getNom())) {

            // Valide le cas dans lequel la catégorie a changé de nom
            validation.addError(FournisseurConstantes.ERROR_FOURNISSEUR_NOM_UNIQUE, FournisseurConstantes.FIELD_NOM);
        }

        validation.execute();

        return this.fournisseurRepository.modify(fournisseur);
    }

    @Override
    public void delete(Long id) {
        Validation.of(FournisseurServiceImpl.class)
                .validateNotNull(id, FournisseurConstantes.FIELD_ID)
                .execute();

        this.validateExist(id, DeleteWithInexistingIdException::new);
        this.validatePasUtiliseParPiece(id);

        this.fournisseurRepository.delete(id);
    }

    /**
     * Valide que le fournisseur existe
     * @param id Id du fournisseur à supprimer
     * @param exception Exception
     */
    private void validateExist(Long id, PerformActionWithInexistingIdFunction exception) {
        this.fournisseurRepository
                .get(id)
                .orElseThrow(() -> exception.instantiate(id, Fournisseur.class));
    }

    /**
     * Valide que le fournisseur n'est pas utilisé par une pièce
     * @param id Id du fournisseur à supprimer
     */
    private void validatePasUtiliseParPiece(Long id) {
        if (this.pieceRepository.existByIdFournisseur(id)) {
            throw new ValidationException(new Error(
                    FournisseurConstantes.ERROR_SUPPRESSION_FOURNISSEUR_IMPOSSIBLE_EXISTE_PIECE,
                    FournisseurConstantes.FIELD_FOURNISSEUR,
                    FournisseurServiceImpl.class));
        }
    }
}
