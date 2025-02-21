package ch.glauser.gestionstock.fournisseur.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.fournisseur.entity.FournisseurEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * JPA Repository pour la gestion des fournisseurs
 */
@Repository
public interface FournisseurJpaRepository extends JpaRepository<FournisseurEntity, Long>, JpaSpecificationExecutor<FournisseurEntity> {
    /**
     * Récupère un fournisseur par id
     *
     * @param id Id du fournisseur
     * @return Un {@link Optional} de {@link FournisseurEntity}
     */
    Optional<FournisseurEntity> findOptionalById(Long id);

    /**
     * Vérifie s'il existe un fournisseur avec cette localité
     *
     * @param id Id de la localité
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(fournisseur) > 0 FROM Fournisseur fournisseur WHERE fournisseur.adresse.localite.id = :id")
    boolean existsByIdLocalite(@Param("id") Long id);

    default Page<FournisseurEntity> search(Collection<Filter> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
