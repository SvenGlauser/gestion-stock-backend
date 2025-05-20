package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.piece.entity.PieceHistoriqueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * JPA Repository pour la gestion des mouvements de pièces
 */
@Repository
public interface PieceHistoriqueJpaRepository extends JpaRepository<PieceHistoriqueEntity, Long>, JpaSpecificationExecutor<PieceHistoriqueEntity> {
    /**
     * Supprime tout l'historique d'une pièce
     * @param idPiece Id de la pièce
     */
    @Modifying
    @Query("DELETE FROM PieceHistorique pieceHistorique WHERE pieceHistorique.piece.id = :idPiece")
    void deleteAllByPieceId(@Param("idPiece") Long idPiece);

    default Page<PieceHistoriqueEntity> search(Collection<FilterCombinator> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
