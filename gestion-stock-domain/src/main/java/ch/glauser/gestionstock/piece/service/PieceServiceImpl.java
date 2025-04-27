package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.categorie.service.CategorieService;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceConstantes;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueConstantes;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueSource;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des pièces
 */
@RequiredArgsConstructor
public class PieceServiceImpl implements PieceService {

    private final PieceRepository pieceRepository;

    private final PieceHistoriqueService pieceHistoriqueService;
    private final CategorieService categorieService;
    private final MachineRepository machineRepository;

    @Override
    public Piece getPiece(Long id) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(id, PieceConstantes.FIELD_ID)
                .execute();

        return this.pieceRepository.getPiece(id);
    }

    @Override
    public SearchResult<Piece> searchPiece(SearchRequest searchRequest) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(searchRequest, PieceConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.pieceRepository.searchPiece(searchRequest);
    }

    @Override
    public SearchResult<Piece> autocompletePiece(String searchValue) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(searchValue, PieceConstantes.FIELD_SEARCH_VALUE)
                .execute();

        return this.pieceRepository.autocompletePiece(searchValue);
    }

    @Override
    public Piece createPiece(Piece piece) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .execute();

        // Validation
        Validation validation = piece.validateCreate();

        if (this.pieceRepository.existPieceByNumeroInventaire(piece.getNumeroInventaire())) {
            validation.addError(PieceConstantes.ERROR_PIECE_NUMERO_INVENTAIRE_UNIQUE, PieceConstantes.FIELD_NUMERO_INVENTAIRE);
        }

        this.validateCategorieActive(piece, validation);

        validation.execute();

        // Création de la pièce
        Piece newPiece = this.pieceRepository.createPiece(piece);

        // Mise à jour de l'historique
        this.pieceHistoriqueService.createPieceHistoriqueFromPiece(newPiece);

        return newPiece;
    }

    @Override
    public Piece modifyPiece(Piece piece) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .execute();

        return this.modifyPiece(piece, PieceHistoriqueSource.MODIFICATION);
    }

    @Override
    public Piece modifyPiece(Piece piece, PieceHistoriqueSource source) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .validateNotNull(source, PieceHistoriqueConstantes.FIELD_SOURCE)
                .execute();

        // Récupération de l'ancienne pièce
        Piece oldPiece = this.pieceRepository.getPiece(piece.getId());

        // Validation
        Validation validation = piece.validateModify();

        if (Objects.nonNull(oldPiece)) {
            if (!Objects.equals(oldPiece.getNumeroInventaire(), piece.getNumeroInventaire()) &&
                    this.pieceRepository.existPieceByNumeroInventaire(piece.getNumeroInventaire())) {

                // Valide le cas dans lequel la pièce a changé de numéro d'inventaire
                validation.addError(PieceConstantes.ERROR_PIECE_NUMERO_INVENTAIRE_UNIQUE, PieceConstantes.FIELD_NUMERO_INVENTAIRE);
            }
        }

        this.validateCategorieActive(piece, validation);

        validation.execute();

        // Création de la pièce
        Piece newPiece = this.pieceRepository.modifyPiece(piece);

        // Mise à jour de l'historique
        this.pieceHistoriqueService.createPieceHistoriqueFromPiece(newPiece, oldPiece, source);

        return newPiece;
    }

    @Override
    public void deletePiece(Long id) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(id, PieceConstantes.FIELD_ID)
                .execute();

        this.validatePieceExist(id);
        this.validatePasUtiliseParMachine(id);

        this.pieceHistoriqueService.deleteAllByIdPiece(id);
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

    /**
     * Valide que la catégorie assignée est bien active
     * @param piece Pièce
     * @param validation Validateur
     */
    private void validateCategorieActive(Piece piece, Validation validation) {
        if (Objects.isNull(piece.getCategorie())) {
            return;
        }

        Categorie categorie = this.categorieService.getCategorie(piece.getCategorie().getId());
        if (Objects.nonNull(categorie) && Boolean.FALSE == categorie.getActif()) {
            validation.addError(PieceConstantes.ERROR_CATEGORIE_DOIT_ETRE_ACTIVE, PieceConstantes.FIELD_CATEGORIE);
        }
    }
}
