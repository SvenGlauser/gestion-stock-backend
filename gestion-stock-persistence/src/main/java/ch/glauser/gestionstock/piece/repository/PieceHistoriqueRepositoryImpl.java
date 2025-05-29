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

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du repository de gestion des mouvements de pieces
 */
@Repository
@RequiredArgsConstructor
public class PieceHistoriqueRepositoryImpl implements PieceHistoriqueRepository {

    private final PieceHistoriqueJpaRepository pieceHistoriqueJpaRepository;

    @Override
    public Optional<PieceHistorique> get(Long id) {
        return this.pieceHistoriqueJpaRepository.findById(id).map(ModelEntity::toDomain);
    }

    @Override
    public PieceHistorique getLastHistoriqueFromPieceId(Long idPiece) {
        return this.pieceHistoriqueJpaRepository.getLastHistoriqueFromPieceId(idPiece).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<PieceHistorique> search(SearchRequest searchRequest) {
        Page<PieceHistoriqueEntity> page = this.pieceHistoriqueJpaRepository.search(PageUtils.getFiltersCombinators(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public List<PieceHistorique> findAllByIdPiece(Long idPiece) {
        return this.pieceHistoriqueJpaRepository.findAllByIdPiece(idPiece).stream().map(ModelEntity::toDomain).toList();
    }

    @Override
    public void create(PieceHistorique pieceHistorique) {
        this.pieceHistoriqueJpaRepository.save(new PieceHistoriqueEntity(pieceHistorique));
    }

    @Override
    public void delete(Long id) {
        this.pieceHistoriqueJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAllByIdPiece(Long idPiece) {
        this.pieceHistoriqueJpaRepository.deleteAllByPieceId(idPiece);
    }
}
