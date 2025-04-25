package ch.glauser.gestionstock.categorie.repository;

import ch.glauser.gestionstock.categorie.entity.CategorieEntity;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * JPA Repository pour la gestion des categories
 */
@Repository
public interface CategorieJpaRepository extends JpaRepository<CategorieEntity, Long>, JpaSpecificationExecutor<CategorieEntity> {
    /**
     * Récupère une catégorie par id
     *
     * @param id Id de la catégorie
     * @return Un {@link Optional} de {@link CategorieEntity}
     */
    Optional<CategorieEntity> findOptionalById(Long id);

    /**
     * Vérifie s'il existe une catégorie avec ce nom
     *
     * @param nom Nom de la catégorie
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existsByNom(String nom);

    default Page<CategorieEntity> search(Collection<FilterCombinator> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
