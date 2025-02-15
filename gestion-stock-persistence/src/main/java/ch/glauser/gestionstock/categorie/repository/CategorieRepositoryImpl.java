package ch.glauser.gestionstock.categorie.repository;

import ch.glauser.gestionstock.categorie.entity.CategorieEntity;
import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * Implémentation du repository de gestion des catégories
 */
@Repository
public class CategorieRepositoryImpl implements CategorieRepository {

    private final CategorieJpaRepository categorieJpaRepository;

    public CategorieRepositoryImpl(CategorieJpaRepository categorieJpaRepository) {
        this.categorieJpaRepository = categorieJpaRepository;
    }

    @Override
    public Categorie getCategorie(Long id) {
        return this.categorieJpaRepository.findOptionalById(id).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<Categorie> searchCategorie(SearchRequest searchRequest) {
        Page<CategorieEntity> page = this.categorieJpaRepository.search(PageUtils.getFilters(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Categorie createCategorie(Categorie categorie) {
        return this.categorieJpaRepository.save(new CategorieEntity(categorie)).toDomain();
    }

    @Override
    public Categorie modifyCategorie(Categorie categorie) {
        return this.categorieJpaRepository.save(new CategorieEntity(categorie)).toDomain();
    }

    @Override
    public void deleteCategorie(Long id) {
        this.categorieJpaRepository.deleteById(id);
    }
}
