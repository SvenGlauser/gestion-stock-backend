package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.exception.id.SearchWithInexistingIdExceptionPerform;
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
    public PieceHistorique get(Long id) {
        Validation.of(PieceHistoriqueServiceImpl.class)
                .validateNotNull(id, PieceHistoriqueConstantes.FIELD_ID)
                .execute();

        return pieceHistoriqueRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, PieceHistorique.class));
    }

    @Override
    public SearchResult<PieceHistorique> search(SearchRequest searchRequest) {
        Validation.of(PieceHistoriqueServiceImpl.class)
                .validateNotNull(searchRequest, PieceHistoriqueConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.pieceHistoriqueRepository.search(searchRequest);
    }

    @Override
    public void createFromPiece(Piece newPiece) {
        Validation.of(PieceHistoriqueServiceImpl.class)
                .validateNotNull(newPiece, PieceHistoriqueConstantes.FIELD_NEW_PIECE)
                .execute();

        PieceHistorique pieceHistorique = new PieceHistorique();
        pieceHistorique.setPiece(newPiece);
        pieceHistorique.setDifference(newPiece.getQuantite());
        pieceHistorique.setType(PieceHistoriqueType.ENTREE);
        pieceHistorique.setSource(PieceHistoriqueSource.CREATION);
        pieceHistorique.setDate(LocalDate.now());

        Validation validation = pieceHistorique.validateCreate();

        validation.execute();

        this.pieceHistoriqueRepository.create(pieceHistorique);
    }

    @Override
    public void createFromPiece(Piece newPiece, Piece oldPiece, PieceHistoriqueSource source) {
        Validation.of(PieceHistoriqueServiceImpl.class)
                .validateNotNull(newPiece, PieceHistoriqueConstantes.FIELD_NEW_PIECE)
                .validateNotNull(oldPiece, PieceHistoriqueConstantes.FIELD_OLD_PIECE)
                .validateNotNull(source, PieceHistoriqueConstantes.FIELD_SOURCE)
                .execute();

        PieceHistorique pieceHistorique = new PieceHistorique();

        long deltaQuantite = newPiece.getQuantite() - oldPiece.getQuantite();

        if (deltaQuantite > 0) {
            pieceHistorique.setType(PieceHistoriqueType.ENTREE);
        } else if (deltaQuantite < 0) {
            pieceHistorique.setType(PieceHistoriqueType.SORTIE);
        } else {
            // Pas de besoin de créer un historique
            return;
        }

        pieceHistorique.setPiece(newPiece);
        pieceHistorique.setDifference(Math.abs(deltaQuantite));
        pieceHistorique.setSource(source);
        pieceHistorique.setDate(LocalDate.now());

        Validation validation = pieceHistorique.validateCreate();

        validation.execute();

        this.pieceHistoriqueRepository.create(pieceHistorique);
    }

    @Override
    public void deleteAllByIdPiece(Long idPiece) {
        Validation.of(PieceHistoriqueServiceImpl.class)
                .validateNotNull(idPiece, PieceHistoriqueConstantes.FIELD_PIECE_ID)
                .execute();

        this.pieceHistoriqueRepository.deleteAllByIdPiece(idPiece);
    }
}
