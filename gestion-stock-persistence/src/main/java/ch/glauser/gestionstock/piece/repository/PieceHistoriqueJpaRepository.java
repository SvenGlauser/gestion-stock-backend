package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import ch.glauser.gestionstock.piece.entity.PieceHistoriqueEntity;
import ch.glauser.gestionstock.piece.model.Piece;
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
 * JPA Repository pour la gestion des mouvements de pièces
 */
@Repository
public interface PieceHistoriqueJpaRepository extends JpaRepository<PieceHistoriqueEntity, Long>, JpaSpecificationExecutor<PieceHistoriqueEntity> {
    /**
     * Récupère un historique de pièce par id
     *
     * @param id Id de l'historique
     * @return Un {@link Optional} de {@link PieceHistoriqueEntity}
     */
    Optional<PieceHistoriqueEntity> findOptionalById(Long id);

    /**
     * Supprime tout l'historique d'une pièce
     * @param idPiece Id de la pièce
     */
    @Query("DELETE FROM PieceHistorique pieceHistorique WHERE pieceHistorique.piece.id = :idPiece")
    void deleteAllByPieceId(@Param("idPiece") Long idPiece);

    default Page<PieceHistoriqueEntity> search(Collection<Filter> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }

    Piece piece(PieceEntity piece);
}
