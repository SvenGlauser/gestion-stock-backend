package ch.glauser.gestionstock.pays.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.pays.entity.PaysEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * JPA Repository pour la gestion des pays
 */
@Repository
public interface PaysJpaRepository extends JpaRepository<PaysEntity, Long>, JpaSpecificationExecutor<PaysEntity> {
    /**
     * Récupère un pays par id
     *
     * @param id Id du pays
     * @return Un {@link Optional} de {@link PaysEntity}
     */
    Optional<PaysEntity> findOptionalById(Long id);

    /**
     * Vérifie s'il existe un pays avec ce nom
     *
     * @param nom Nom du pays
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existsByNom(String nom);

    /**
     * Vérifie s'il existe un pays avec cette abréviation
     *
     * @param abreviation Abréviation du pays
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existsByAbreviation(String abreviation);

    default Page<PaysEntity> search(Collection<Filter> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
