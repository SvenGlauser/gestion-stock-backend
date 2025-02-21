package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import ch.glauser.gestionstock.piece.model.Piece;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * Implémentation du repository de gestion des pieces
 */
@Repository
public class PieceRepositoryImpl implements PieceRepository {

    private final PieceJpaRepository pieceJpaRepository;

    public PieceRepositoryImpl(PieceJpaRepository pieceJpaRepository) {
        this.pieceJpaRepository = pieceJpaRepository;
    }

    @Override
    public Piece getPiece(Long id) {
        return this.pieceJpaRepository.findOptionalById(id).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<Piece> searchPiece(SearchRequest searchRequest) {
        Page<PieceEntity> page = this.pieceJpaRepository.search(PageUtils.getFilters(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Piece createPiece(Piece piece) {
        return this.pieceJpaRepository.save(new PieceEntity(piece)).toDomain();
    }

    @Override
    public Piece modifyPiece(Piece piece) {
        return this.pieceJpaRepository.save(new PieceEntity(piece)).toDomain();
    }

    @Override
    public void deletePiece(Long id) {
        this.pieceJpaRepository.deleteById(id);
    }

    @Override
    public boolean existPieceWithIdCategorie(Long id) {
        return this.pieceJpaRepository.existsByIdCategorie(id);
    }
}
