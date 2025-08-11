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
import java.util.List;
import java.util.Optional;

/**
 * JPA Repository pour la gestion des mouvements de pièces
 */
@Repository
public interface PieceHistoriqueJpaRepository extends JpaRepository<PieceHistoriqueEntity, Long>, JpaSpecificationExecutor<PieceHistoriqueEntity> {

    @Query("SELECT historique FROM PieceHistorique historique WHERE historique.piece.id = :idPiece ORDER BY historique.heure DESC LIMIT 1")
    Optional<PieceHistoriqueEntity> getLastHistoriqueFromPieceId(@Param("idPiece") Long idPiece);

    /**
     * Recherche tout l'historique d'une pièce
     * @param idPiece Id de la pièce
     * @return Une liste d'historique
     */
    @Query("SELECT historique FROM PieceHistorique historique WHERE historique.piece.id = :idPiece")
    List<PieceHistoriqueEntity> findAllByIdPiece(@Param("idPiece") Long idPiece);

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
