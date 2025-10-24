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
import java.util.Set;

/**
 * JPA Repository pour la gestion des fournisseurs
 */
@Repository
public interface FournisseurJpaRepository extends JpaRepository<FournisseurEntity, Long>, JpaSpecificationExecutor<FournisseurEntity> {
    /**
     * Récupère un fournisseur
     *
     * @param designation Désignation de l'identité liée au fournisseur (Nom prénom ou Raison sociale)
     * @return La fournisseur ou null
     */
    @Query("""
            SELECT fournisseur
            FROM Fournisseur fournisseur
            WHERE LOWER(fournisseur.identite.designation) = LOWER(:designation)""")
    Set<FournisseurEntity> findAllByIdentiteDesignation(@Param("designation") String designation);

    /**
     * Vérifie s'il existe un fournisseur avec cette identité
     *
     * @param id Id de l'identité
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    @Query("""
            SELECT COUNT(fournisseur) > 0
            FROM Fournisseur fournisseur
            WHERE fournisseur.identite.id = :id""")
    boolean existsByIdIdentite(@Param("id") Long id);

    default Page<FournisseurEntity> search(Collection<FilterCombinator> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
