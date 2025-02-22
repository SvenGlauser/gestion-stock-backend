package ch.glauser.gestionstock.categorie.service;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.categorie.model.CategorieConstantes;
import ch.glauser.gestionstock.categorie.repository.CategorieRepository;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
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
    public Categorie getCategorie(Long id) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(id, CategorieConstantes.FIELD_ID)
                .execute();

        return this.categorieRepository.getCategorie(id);
    }

    @Override
    public SearchResult<Categorie> searchCategorie(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, CategorieConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.categorieRepository.searchCategorie(searchRequest);
    }

    @Override
    public Categorie createCategorie(Categorie categorie) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(categorie, CategorieConstantes.FIELD_CATEGORIE)
                .execute();

        Validator validator = categorie.validateCreate();

        if (this.categorieRepository.existCategorieByNom(categorie.getNom())) {
            validator.addError(CategorieConstantes.ERROR_CATEGORIE_NOM_UNIQUE, CategorieConstantes.FIELD_NOM);
        }

        validator.execute();

        return this.categorieRepository.createCategorie(categorie);
    }

    @Override
    public Categorie modifyCategorie(Categorie categorie) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(categorie, CategorieConstantes.FIELD_CATEGORIE)
                .execute();

        Categorie oldCategorie = this.categorieRepository.getCategorie(categorie.getId());

        Validator validator = categorie.validateModify();

        if (Objects.nonNull(oldCategorie) &&
            !Objects.equals(oldCategorie.getNom(), categorie.getNom()) &&
            this.categorieRepository.existCategorieByNom(categorie.getNom())) {

            // Valide le cas dans lequel la catégorie a changé de nom
            validator.addError(CategorieConstantes.ERROR_CATEGORIE_NOM_UNIQUE, CategorieConstantes.FIELD_NOM);
        }

        validator.execute();

        return this.categorieRepository.modifyCategorie(categorie);
    }

    @Override
    public void deleteCategorie(Long id) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(id, CategorieConstantes.FIELD_ID)
                .execute();

        this.validateCategorieExist(id);
        this.validatePasUtiliseParPiece(id);

        this.categorieRepository.deleteCategorie(id);
    }

    /**
     * Valide que la catégorie existe
     * @param id Id de la catégorie à supprimer
     */
    private void validateCategorieExist(Long id) {
        Categorie categorieToDelete = this.getCategorie(id);

        if (Objects.isNull(categorieToDelete)) {
            throw new ValidationException(new Error(
                    CategorieConstantes.ERROR_SUPPRESSION_CATEGORIE_INEXISTANTE,
                    CategorieConstantes.FIELD_CATEGORIE,
                    CategorieServiceImpl.class));
        }
    }

    /**
     * Valide que la catégorie n'est pas utilisé par une pièce
     * @param id Id de la catégorie à valider
     */
    private void validatePasUtiliseParPiece(Long id) {
        if (this.pieceRepository.existPieceByIdCategorie(id)) {
            throw new ValidationException(new Error(
                    CategorieConstantes.ERROR_SUPPRESSION_CATEGORIE_IMPOSSIBLE_EXISTE_PIECE,
                    CategorieConstantes.FIELD_CATEGORIE,
                    CategorieServiceImpl.class));
        }
    }
}
