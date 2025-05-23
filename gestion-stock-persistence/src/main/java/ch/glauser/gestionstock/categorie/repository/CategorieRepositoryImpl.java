package ch.glauser.gestionstock.categorie.repository;

import ch.glauser.gestionstock.categorie.entity.CategorieEntity;
import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public Optional<Categorie> get(Long id) {
        return this.categorieJpaRepository.findById(id).map(ModelEntity::toDomain);
    }

    @Override
    public SearchResult<Categorie> search(SearchRequest searchRequest) {
        Page<CategorieEntity> page = this.categorieJpaRepository.search(PageUtils.getFiltersCombinators(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Categorie create(Categorie categorie) {
        return this.categorieJpaRepository.save(new CategorieEntity(categorie)).toDomain();
    }

    @Override
    public Categorie modify(Categorie categorie) {
        return this.categorieJpaRepository.save(new CategorieEntity(categorie)).toDomain();
    }

    @Override
    public void delete(Long id) {
        this.categorieJpaRepository.deleteById(id);
    }

    @Override
    public boolean existByNom(String nom) {
        return this.categorieJpaRepository.existsByNom(nom);
    }
}
