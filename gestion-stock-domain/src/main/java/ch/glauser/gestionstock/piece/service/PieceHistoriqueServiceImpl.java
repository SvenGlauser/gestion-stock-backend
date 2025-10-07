package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.model.*;
import ch.glauser.gestionstock.piece.repository.PieceHistoriqueRepository;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import ch.glauser.utilities.exception.TechnicalException;
import ch.glauser.validation.common.Error;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Implémentation du service de gestion des mouvements de pièces
 */
@RequiredArgsConstructor
public class PieceHistoriqueServiceImpl implements PieceHistoriqueService {

    private final PieceHistoriqueRepository pieceHistoriqueRepository;
    private final PieceRepository pieceRepository;

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
        pieceHistorique.setHeure(LocalDateTime.now());

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
        pieceHistorique.setHeure(LocalDateTime.now());

        Validation validation = pieceHistorique.validateCreate();

        validation.execute();

        this.pieceHistoriqueRepository.create(pieceHistorique);
    }

    @Override
    public void delete(Long idPieceHistorique) {
        Validation.of(PieceHistoriqueServiceImpl.class)
                .validateNotNull(idPieceHistorique, PieceHistoriqueConstantes.FIELD_ID)
                .execute();

        PieceHistorique pieceHistorique = this.pieceHistoriqueRepository
                .get(idPieceHistorique)
                .orElseThrow(() -> new DeleteWithInexistingIdException(idPieceHistorique, PieceHistorique.class));

        PieceHistorique lastPieceHistorique = this.pieceHistoriqueRepository.getLastHistoriqueFromPieceId(pieceHistorique.getPiece().getId());

        if (lastPieceHistorique.getId().equals(pieceHistorique.getId())) {
            Piece piece = this.pieceRepository
                    .get(pieceHistorique.getPiece().getId())
                    .orElseThrow(() -> new TechnicalException("Impossible de récupérer la pièce lié"));
            if (pieceHistorique.getType() == PieceHistoriqueType.ENTREE) {
                piece.setQuantite(piece.getQuantite() - pieceHistorique.getDifference());
            } else if (pieceHistorique.getType() == PieceHistoriqueType.SORTIE) {
                piece.setQuantite(piece.getQuantite() + pieceHistorique.getDifference());
            }

            this.pieceRepository.modify(piece);
            this.pieceHistoriqueRepository.delete(pieceHistorique.getId());
        } else {
            throw new ValidationException(new Error(
                    PieceHistoriqueConstantes.ERROR_IMPOSSIBLE_SUPPRIMER_HISTORIQUE_PIECE_NON_DERNIER,
                    PieceHistoriqueConstantes.FIELD_PIECE_HISTORIQUE,
                    PieceHistorique.class));
        }
    }

    @Override
    public void deleteAllByIdPiece(Long idPiece) {
        Validation.of(PieceHistoriqueServiceImpl.class)
                .validateNotNull(idPiece, PieceHistoriqueConstantes.FIELD_PIECE_ID)
                .execute();

        this.pieceHistoriqueRepository.deleteAllByIdPiece(idPiece);
    }
}
