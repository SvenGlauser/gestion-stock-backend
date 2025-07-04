package ch.glauser.gestionstock.fournisseur.repository;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
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

/**
 * JPA Repository pour la gestion des fournisseurs
 */
@Repository
public interface FournisseurJpaRepository extends JpaRepository<FournisseurEntity, Long>, JpaSpecificationExecutor<FournisseurEntity> {
    /**
     * Vérifie s'il existe un fournisseur avec cette localité
     *
     * @param id Id de la localité
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(fournisseur) > 0 FROM Fournisseur fournisseur WHERE fournisseur.adresse.localite.id = :id")
    boolean existsByIdLocalite(@Param("id") Long id);

    /**
     * Vérifie s'il existe un fournisseur avec ce nom
     *
     * @param nom Nom du fournisseur
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existsByNom(String nom);

    default Page<FournisseurEntity> search(Collection<FilterCombinator> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
