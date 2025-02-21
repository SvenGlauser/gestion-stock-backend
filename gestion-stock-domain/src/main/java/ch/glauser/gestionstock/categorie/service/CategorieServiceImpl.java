package ch.glauser.gestionstock.categorie.service;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.categorie.repository.CategorieRepository;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Validator;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des catégories
 */
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CATEGORIE = "categorie";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final CategorieRepository categorieRepository;

    @Override
    public Categorie getCategorie(Long id) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.categorieRepository.getCategorie(id);
    }

    @Override
    public SearchResult<Categorie> searchCategorie(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.categorieRepository.searchCategorie(searchRequest);
    }

    @Override
    public Categorie createCategorie(Categorie categorie) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(categorie, FIELD_CATEGORIE)
                .execute();

        categorie.validate();

        return this.categorieRepository.createCategorie(categorie);
    }

    @Override
    public Categorie modifyCategorie(Categorie categorie) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(categorie, FIELD_CATEGORIE)
                .execute();

        categorie.validateModify();

        return this.categorieRepository.modifyCategorie(categorie);
    }

    @Override
    public void deleteCategorie(Long id) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.categorieRepository.deleteCategorie(id);
    }
}
