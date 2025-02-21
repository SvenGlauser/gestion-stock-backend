package ch.glauser.gestionstock.localite.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.localite.entity.LocaliteEntity;
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
 * JPA Repository pour la gestion des localités
 */
@Repository
public interface LocaliteJpaRepository extends JpaRepository<LocaliteEntity, Long>, JpaSpecificationExecutor<LocaliteEntity> {
    /**
     * Récupère une localité par id
     *
     * @param id Id de la localité
     * @return Un {@link Optional} de {@link LocaliteEntity}
     */
    Optional<LocaliteEntity> findOptionalById(Long id);

    /**
     * Vérifie s'il existe une localité avec ce pays
     *
     * @param id Id du pays
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(localite) > 0 FROM Localite localite WHERE localite.pays.id = :id")
    boolean existsByIdPays(@Param("id") Long id);

    default Page<LocaliteEntity> search(Collection<Filter> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
