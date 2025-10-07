package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.categorie.service.CategorieService;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.ModifyWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.PerformActionWithInexistingIdFunction;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import ch.glauser.gestionstock.machine.repository.MachineRepository;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceConstantes;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueConstantes;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueSource;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import ch.glauser.validation.common.Error;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
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
    public Piece get(Long id) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(id, PieceConstantes.FIELD_ID)
                .execute();

        return this.pieceRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, Piece.class));
    }

    @Override
    public SearchResult<Piece> search(SearchRequest searchRequest) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(searchRequest, PieceConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.pieceRepository.search(searchRequest);
    }

    @Override
    public SearchResult<Piece> autocomplete(String searchValue) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(searchValue, PieceConstantes.FIELD_SEARCH_VALUE)
                .execute();

        return this.pieceRepository.autocomplete(searchValue);
    }

    @Override
    public Piece create(Piece piece) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .execute();

        // Validation
        Validation validation = piece.validateCreate();

        if (this.pieceRepository.existByNumeroInventaire(piece.getNumeroInventaire())) {
            validation.addError(PieceConstantes.ERROR_PIECE_NUMERO_INVENTAIRE_UNIQUE, PieceConstantes.FIELD_NUMERO_INVENTAIRE);
        }

        this.validateCategorieActive(piece, validation);

        validation.execute();

        // Création de la pièce
        Piece newPiece = this.pieceRepository.create(piece);

        // Mise à jour de l'historique
        this.pieceHistoriqueService.createFromPiece(newPiece);

        return newPiece;
    }

    @Override
    public Piece modify(Piece piece) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .execute();

        return this.modify(piece, PieceHistoriqueSource.MODIFICATION);
    }

    @Override
    public Piece modify(Piece piece, PieceHistoriqueSource source) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(piece, PieceConstantes.FIELD_PIECE)
                .validateNotNull(source, PieceHistoriqueConstantes.FIELD_SOURCE)
                .execute();

        // Récupération de l'ancienne pièce
        Piece oldPiece = this.pieceRepository
                .get(piece.getId())
                .orElseThrow(() -> new ModifyWithInexistingIdException(piece.getId(), Piece.class));

        // Validation
        Validation validation = piece.validateModify();

        if (!Objects.equals(oldPiece.getNumeroInventaire(), piece.getNumeroInventaire()) &&
                this.pieceRepository.existByNumeroInventaire(piece.getNumeroInventaire())) {

            // Valide le cas dans lequel la pièce a changé de numéro d'inventaire
            validation.addError(PieceConstantes.ERROR_PIECE_NUMERO_INVENTAIRE_UNIQUE, PieceConstantes.FIELD_NUMERO_INVENTAIRE);
        }

        this.validateCategorieActive(piece, validation);

        validation.execute();

        // Création de la pièce
        Piece newPiece = this.pieceRepository.modify(piece);

        // Mise à jour de l'historique
        this.pieceHistoriqueService.createFromPiece(newPiece, oldPiece, source);

        return newPiece;
    }

    @Override
    public void delete(Long id) {
        Validation.of(PieceServiceImpl.class)
                .validateNotNull(id, PieceConstantes.FIELD_ID)
                .execute();

        this.validateExist(id, DeleteWithInexistingIdException::new);
        this.validatePasUtiliseParMachine(id);

        this.pieceHistoriqueService.deleteAllByIdPiece(id);
        this.pieceRepository.delete(id);
    }

    /**
     * Valide que la pièce existe
     * @param id Id de la pièce à supprimer
     * @param exception L'exception à instancier
     */
    private void validateExist(Long id, PerformActionWithInexistingIdFunction exception) {
        this.pieceRepository
                .get(id)
                .orElseThrow(() -> exception.instantiate(id, PersonnePhysique.class));
    }

    /**
     * Valide que la pièce n'est pas utilisé par une machine
     * @param id Id de la pièce à supprimer
     */
    private void validatePasUtiliseParMachine(Long id) {
        if (this.machineRepository.existByIdPiece(id)) {
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

        Categorie categorie = this.categorieService.get(piece.getCategorie().getId());
        if (Objects.nonNull(categorie) && Boolean.FALSE == categorie.getActif()) {
            validation.addError(PieceConstantes.ERROR_CATEGORIE_DOIT_ETRE_ACTIVE, PieceConstantes.FIELD_CATEGORIE);
        }
    }
}
