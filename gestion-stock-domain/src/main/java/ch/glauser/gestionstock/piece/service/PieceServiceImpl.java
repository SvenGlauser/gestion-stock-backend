package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceConstantes;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des pièces
 */
@RequiredArgsConstructor
public class PieceServiceImpl implements PieceService {

    private final PieceRepository pieceRepository;

    private final MachineRepository machineRepository;

    @Override
    public Piece getPiece(Long id) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(id, PieceConstantes.FIELD_ID)
                .execute();

        return this.pieceRepository.getPiece(id);
    }

    @Override
    public SearchResult<Piece> searchPiece(SearchRequest searchRequest) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(searchRequest, PieceConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.pieceRepository.searchPiece(searchRequest);
    }

    @Override
    public Piece createPiece(Piece piece) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .execute();

        Validator validator = piece.validateCreate();

        if (this.pieceRepository.existPieceByNom(piece.getNom())) {
            validator.addError(PieceConstantes.ERROR_PIECE_NOM_UNIQUE, PieceConstantes.FIELD_NOM);
        }

        if (this.pieceRepository.existPieceByNumeroInventaire(piece.getNumeroInventaire())) {
            validator.addError(PieceConstantes.ERROR_PIECE_NUMERO_INVENTAIRE_UNIQUE, PieceConstantes.FIELD_NUMERO_INVENTAIRE);
        }

        validator.execute();

        return this.pieceRepository.createPiece(piece);
    }

    @Override
    public Piece modifyPiece(Piece piece) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .execute();

        Piece oldPiece = this.pieceRepository.getPiece(piece.getId());

        Validator validator = piece.validateModify();

        if (Objects.nonNull(oldPiece)) {
            if (!Objects.equals(oldPiece.getNom(), piece.getNom()) &&
                this.pieceRepository.existPieceByNom(piece.getNom())) {

                // Valide le cas dans lequel la pièce a changé de nom
                validator.addError(PieceConstantes.ERROR_PIECE_NOM_UNIQUE, PieceConstantes.FIELD_NOM);
            }

            if (!Objects.equals(oldPiece.getNumeroInventaire(), piece.getNumeroInventaire()) &&
                this.pieceRepository.existPieceByNumeroInventaire(piece.getNumeroInventaire())) {

                // Valide le cas dans lequel la pièce a changé de numéro d'inventaire
                validator.addError(PieceConstantes.ERROR_PIECE_NUMERO_INVENTAIRE_UNIQUE, PieceConstantes.FIELD_NUMERO_INVENTAIRE);
            }
        }

        validator.execute();

        return this.pieceRepository.modifyPiece(piece);
    }

    @Override
    public void deletePiece(Long id) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(id, PieceConstantes.FIELD_ID)
                .execute();

        this.validatePieceExist(id);
        this.validatePasUtiliseParMachine(id);

        this.pieceRepository.deletePiece(id);
    }

    /**
     * Valide que la pièce existe
     * @param id Id de la pièce à supprimer
     */
    private void validatePieceExist(Long id) {
        Piece pieceToDelete = this.getPiece(id);

        if (Objects.isNull(pieceToDelete)) {
            throw new ValidationException(new Error(
                    PieceConstantes.ERROR_SUPPRESSION_PIECE_INEXISTANTE,
                    PieceConstantes.FIELD_PIECE,
                    PieceServiceImpl.class));
        }
    }

    /**
     * Valide que la pièce n'est pas utilisé par une machine
     * @param id Id de la pièce à supprimer
     */
    private void validatePasUtiliseParMachine(Long id) {
        if (this.machineRepository.existMachineByIdPiece(id)) {
            throw new ValidationException(new Error(
                    PieceConstantes.ERROR_SUPPRESSION_PIECE_IMPOSSIBLE_EXISTE_MACHINE,
                    PieceConstantes.FIELD_PIECE,
                    PieceServiceImpl.class));
        }
    }
}
