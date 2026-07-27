package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.maxlength.MaxLength;
import ch.glauser.validation.minvalue.MinValue;
import ch.glauser.validation.notempty.NotEmpty;
import ch.glauser.validation.notnull.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChangementPiece extends Model {
    @NotNull
    private Piece piece;

    @NotNull
    @MinValue(1)
    private Integer quantite;

    @NotEmpty
    @MaxLength(4096)
    private String description;

    @Override
    protected Validation validateChild() {
        return Validation.validate(this, ChangementPiece.class);
    }
}
