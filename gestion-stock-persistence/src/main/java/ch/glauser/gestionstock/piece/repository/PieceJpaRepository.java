package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * JPA Repository pour la gestion des pieces
 */
@Repository
public interface PieceJpaRepository extends JpaRepository<PieceEntity, Long>, JpaSpecificationExecutor<PieceEntity> {
    Optional<PieceEntity> findOptionalById(Long id);

    default Page<PieceEntity> search(Collection<Filter> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
