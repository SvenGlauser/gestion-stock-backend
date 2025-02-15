package ch.glauser.gestionstock.fournisseur.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.fournisseur.entity.FournisseurEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * JPA Repository pour la gestion des fournisseurs
 */
@Repository
public interface FournisseurJpaRepository extends JpaRepository<FournisseurEntity, Long>, JpaSpecificationExecutor<FournisseurEntity> {
    Optional<FournisseurEntity> findOptionalById(Long id);

    default Page<FournisseurEntity> search(Collection<Filter> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
