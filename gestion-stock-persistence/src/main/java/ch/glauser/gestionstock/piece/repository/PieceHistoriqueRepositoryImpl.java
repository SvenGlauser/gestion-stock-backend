package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.entity.PieceHistoriqueEntity;
import ch.glauser.gestionstock.piece.model.PieceHistorique;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * Implémentation du repository de gestion des mouvements de pieces
 */
@Repository
@RequiredArgsConstructor
public class PieceHistoriqueRepositoryImpl implements PieceHistoriqueRepository {

    private final PieceHistoriqueJpaRepository pieceHistoriqueJpaRepository;

    @Override
    public PieceHistorique getPieceHistorique(Long id) {
        return this.pieceHistoriqueJpaRepository.findOptionalById(id).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<PieceHistorique> searchPieceHistorique(SearchRequest searchRequest) {
        Page<PieceHistoriqueEntity> page = this.pieceHistoriqueJpaRepository.search(PageUtils.getFilters(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public void createPieceHistorique(PieceHistorique pieceHistorique) {
        this.pieceHistoriqueJpaRepository.save(new PieceHistoriqueEntity(pieceHistorique));
    }

    @Override
    public void deleteAllByIdPiece(Long idPiece) {
        this.pieceHistoriqueJpaRepository.deleteAllByPieceId(idPiece);
    }
}
