package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * JPA Repository pour la gestion des pieces
 */
@Repository
public interface PieceJpaRepository extends JpaRepository<PieceEntity, Long>, JpaSpecificationExecutor<PieceEntity> {
    /**
     * Vérifie s'il existe une pièce avec cette catégorie
     *
     * @param id Id de la catégorie
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(piece) > 0 FROM Piece piece WHERE piece.categorie.id = :id")
    boolean existsByIdCategorie(@Param("id") Long id);

    /**
     * Vérifie s'il existe une pièce avec ce fournisseur
     *
     * @param id Id du fournisseur
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(piece) > 0 FROM Piece piece WHERE piece.fournisseur.id = :id")
    boolean existsByIdFournisseur(@Param("id") Long id);

    /**
     * Vérifie s'il existe une pièce avec ce numéro d'inventaire
     *
     * @param numeroInventaire Numéro d'inventaire de la catégorie
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existsByNumeroInventaire(String numeroInventaire);

    default Page<PieceEntity> search(Collection<FilterCombinator> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }

    default List<PieceEntity> searchAll(Collection<FilterCombinator> filters) {
        return findAll(RepositoryUtils.specificationOf(filters));
    }
}
