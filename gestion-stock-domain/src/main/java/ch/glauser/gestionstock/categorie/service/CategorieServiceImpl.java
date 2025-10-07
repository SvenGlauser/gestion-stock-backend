package ch.glauser.gestionstock.categorie.service;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.categorie.model.CategorieConstantes;
import ch.glauser.gestionstock.categorie.repository.CategorieRepository;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.ModifyWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.PerformActionWithInexistingIdFunction;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import ch.glauser.validation.common.Error;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des catégories
 */
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    private final PieceRepository pieceRepository;

    @Override
    public Categorie get(Long id) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(id, CategorieConstantes.FIELD_ID)
                .execute();

        return this.categorieRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, Categorie.class));
    }

    @Override
    public SearchResult<Categorie> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, CategorieConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.categorieRepository.search(searchRequest);
    }

    @Override
    public Categorie create(Categorie categorie) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(categorie, CategorieConstantes.FIELD_CATEGORIE)
                .execute();

        Validation validation = categorie.validateCreate();

        if (this.categorieRepository.existByNom(categorie.getNom())) {
            validation.addError(CategorieConstantes.ERROR_CATEGORIE_NOM_UNIQUE, CategorieConstantes.FIELD_NOM);
        }

        validation.execute();

        return this.categorieRepository.create(categorie);
    }

    @Override
    public Categorie modify(Categorie categorie) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(categorie, CategorieConstantes.FIELD_CATEGORIE)
                .execute();

        Categorie oldCategorie = this.categorieRepository
                .get(categorie.getId())
                .orElseThrow(() -> new ModifyWithInexistingIdException(categorie.getId(), Categorie.class));

        Validation validation = categorie.validateModify();

        if (!Objects.equals(oldCategorie.getNom(), categorie.getNom()) &&
            this.categorieRepository.existByNom(categorie.getNom())) {

            // Valide le cas dans lequel la catégorie a changé de nom
            validation.addError(CategorieConstantes.ERROR_CATEGORIE_NOM_UNIQUE, CategorieConstantes.FIELD_NOM);
        }

        validation.execute();

        return this.categorieRepository.modify(categorie);
    }

    @Override
    public void delete(Long id) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(id, CategorieConstantes.FIELD_ID)
                .execute();

        this.validateCategorieExist(id, DeleteWithInexistingIdException::new);
        this.validatePasUtiliseParPiece(id);

        this.categorieRepository.delete(id);
    }

    /**
     * Valide que la catégorie existe
     * @param id Id de la catégorie à supprimer
     * @param exception Exception
     */
    private void validateCategorieExist(Long id, PerformActionWithInexistingIdFunction exception) {
        this.categorieRepository
                .get(id)
                .orElseThrow(() -> exception.instantiate(id, Categorie.class));
    }

    /**
     * Valide que la catégorie n'est pas utilisé par une pièce
     * @param id Id de la catégorie à valider
     */
    private void validatePasUtiliseParPiece(Long id) {
        if (this.pieceRepository.existByIdCategorie(id)) {
            throw new ValidationException(new Error(
                    CategorieConstantes.ERROR_SUPPRESSION_CATEGORIE_IMPOSSIBLE_EXISTE_PIECE,
                    CategorieConstantes.FIELD_CATEGORIE,
                    CategorieServiceImpl.class));
        }
    }
}
