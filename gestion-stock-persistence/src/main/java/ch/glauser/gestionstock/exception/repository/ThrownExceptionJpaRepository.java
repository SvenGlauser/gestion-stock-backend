package ch.glauser.gestionstock.exception.repository;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.exception.entity.ThrownExceptionEntity;
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
public interface ThrownExceptionJpaRepository extends JpaRepository<ThrownExceptionEntity, Long>, JpaSpecificationExecutor<ThrownExceptionEntity> {
    /**
     * Récupère une exception par id
     *
     * @param id Id de l'exception
     * @return Un {@link Optional} de {@link ThrownExceptionEntity}
     */
    Optional<ThrownExceptionEntity> findOptionalById(Long id);

    default Page<ThrownExceptionEntity> search(Collection<FilterCombinator> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
