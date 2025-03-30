package ch.glauser.gestionstock.piece.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.minvalue.MinValue;
import ch.glauser.gestionstock.common.validation.notnull.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Model représentant le mouvement d'une pièce dans l'inventaire
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PieceHistorique extends Model {
    @NotNull
    private Piece piece;

    @MinValue(value = 0)
    private Long difference;

    @NotNull
    private LocalDate date;

    @NotNull
    private PieceHistoriqueType type;
    @NotNull
    private PieceHistoriqueSource source;

    @Override
    protected Validation validateChild() {
        return Validation.validate(this, PieceHistorique.class);
    }
}
