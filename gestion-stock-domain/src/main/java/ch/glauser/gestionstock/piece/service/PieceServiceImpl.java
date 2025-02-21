package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des pièces
 */
@RequiredArgsConstructor
public class PieceServiceImpl implements PieceService {

    public static final String FIELD_PIECE = "piece";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";
    public static final String ERROR_SUPPRESSION_PIECE_INEXISTANTE = "Impossible de supprimer cette pièce car elle n'existe pas";
    public static final String ERROR_SUPPRESSION_PIECE_IMPOSSIBLE_EXISTE_MACHINE = "Impossible de supprimer cette pièce car il existe une machine liée";

    private final PieceRepository pieceRepository;

    private final MachineRepository machineRepository;

    @Override
    public Piece getPiece(Long id) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.pieceRepository.getPiece(id);
    }

    @Override
    public SearchResult<Piece> searchPiece(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.pieceRepository.searchPiece(searchRequest);
    }

    @Override
    public Piece createPiece(Piece piece) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(piece, FIELD_PIECE)
                .execute();

        piece.validateCreate();

        return this.pieceRepository.createPiece(piece);
    }

    @Override
    public Piece modifyPiece(Piece piece) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(piece, FIELD_PIECE)
                .execute();

        piece.validateModify();

        return this.pieceRepository.modifyPiece(piece);
    }

    @Override
    public void deletePiece(Long id) {
        Validator.of(PieceServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
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
                    ERROR_SUPPRESSION_PIECE_INEXISTANTE,
                    FIELD_PIECE,
                    PieceServiceImpl.class));
        }
    }

    /**
     * Valide que la pièce n'est pas utilisé par une machine
     * @param id Id de la pièce à supprimer
     */
    private void validatePasUtiliseParMachine(Long id) {
        if (this.machineRepository.existMachineWithIdPiece(id)) {
            throw new ValidationException(new Error(
                    ERROR_SUPPRESSION_PIECE_IMPOSSIBLE_EXISTE_MACHINE,
                    FIELD_PIECE,
                    PieceServiceImpl.class));
        }
    }
}
