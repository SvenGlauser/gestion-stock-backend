package ch.glauser.gestionstock.identite.repository;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.identite.entity.IdentiteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * JPA Repository pour la gestion des identités
 */
@Repository
public interface IdentiteJpaRepository extends JpaRepository<IdentiteEntity, Long>, JpaSpecificationExecutor<IdentiteEntity> {

    /**
     * Vérifie s'il existe une identité avec cette localité
     *
     * @param id Id de la localité
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    @Query("SELECT COUNT(identite) > 0 FROM Identite identite WHERE identite.adresse.localite.id = :id")
    boolean existsByIdLocalite(@Param("id") Long id);

    default Page<IdentiteEntity> search(Collection<FilterCombinator> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
