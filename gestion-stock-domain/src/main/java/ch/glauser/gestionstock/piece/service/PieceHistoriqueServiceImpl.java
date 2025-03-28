package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.piece.model.*;
import ch.glauser.gestionstock.piece.repository.PieceHistoriqueRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * Implémentation du service de gestion des mouvements de pièces
 */
@RequiredArgsConstructor
public class PieceHistoriqueServiceImpl implements PieceHistoriqueService {

    private final PieceHistoriqueRepository pieceHistoriqueRepository;

    @Override
    public PieceHistorique getPieceHistorique(Long id) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(id, PieceHistoriqueConstantes.FIELD_ID)
                .execute();

        return pieceHistoriqueRepository.getPieceHistorique(id);
    }

    @Override
    public SearchResult<PieceHistorique> searchPieceHistorique(SearchRequest searchRequest) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(searchRequest, PieceHistoriqueConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.pieceHistoriqueRepository.searchPieceHistorique(searchRequest);
    }

    @Override
    public void createPieceHistoriqueFromPiece(Piece newPiece) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(newPiece, PieceHistoriqueConstantes.FIELD_NEW_PIECE)
                .execute();

        PieceHistorique pieceHistorique = new PieceHistorique();
        pieceHistorique.setPiece(newPiece);
        pieceHistorique.setDifference(newPiece.getQuantite());
        pieceHistorique.setType(PieceHistoriqueType.ENTREE);
        pieceHistorique.setSource(PieceHistoriqueSource.AUTOMATIQUE);
        pieceHistorique.setDate(LocalDate.now());

        Validator validator = pieceHistorique.validateCreate();

        validator.execute();

        this.pieceHistoriqueRepository.createPieceHistorique(pieceHistorique);
    }

    @Override
    public void createPieceHistoriqueFromPiece(Piece newPiece, Piece oldPiece) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(newPiece, PieceHistoriqueConstantes.FIELD_NEW_PIECE)
                .validateNotNull(oldPiece, PieceHistoriqueConstantes.FIELD_OLD_PIECE)
                .execute();

        PieceHistorique pieceHistorique = new PieceHistorique();

        long quantite = newPiece.getQuantite() - oldPiece.getQuantite();

        if (quantite > 0) {
            pieceHistorique.setType(PieceHistoriqueType.ENTREE);
        } else if (quantite < 0) {
            pieceHistorique.setType(PieceHistoriqueType.SORTIE);
        } else {
            return;
        }

        pieceHistorique.setPiece(newPiece);
        pieceHistorique.setDifference(Math.abs(quantite));
        pieceHistorique.setSource(PieceHistoriqueSource.AUTOMATIQUE);
        pieceHistorique.setDate(LocalDate.now());

        Validator validator = pieceHistorique.validateCreate();

        validator.execute();

        this.pieceHistoriqueRepository.createPieceHistorique(pieceHistorique);
    }

    @Override
    public void deleteAllByIdPiece(Long idPiece) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(idPiece, PieceHistoriqueConstantes.FIELD_PIECE_ID)
                .execute();

        this.pieceHistoriqueRepository.deleteAllByIdPiece(idPiece);
    }
}
