package ch.glauser.gestionstock.categorie.service;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.categorie.repository.CategorieRepository;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.Validator;

/**
 * Implémentation du service de gestion des catégories
 */
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public Categorie getCategorie(int id) {
        return this.categorieRepository.getCategorie(id);
    }

    @Override
    public SearchResult<Categorie> searchCategorie() {
        return this.categorieRepository.searchCategorie();
    }

    @Override
    public Categorie createCategorie(Categorie categorie) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(categorie, "categorie")
                .execute();

        categorie.validate();

        return this.categorieRepository.createCategorie(categorie);
    }

    @Override
    public Categorie modifyCategorie(Categorie categorie) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(categorie, "categorie")
                .execute();

        categorie.validateModify();

        return this.categorieRepository.modifyCategorie(categorie);
    }

    @Override
    public void deleteCategorie(int id) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(id, "id")
                .execute();

        this.categorieRepository.deleteCategorie(id);
    }
}
