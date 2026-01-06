package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.identite.model.Identite;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.maxlength.MaxLength;
import ch.glauser.validation.notempty.NotEmpty;
import ch.glauser.validation.notnull.NotNull;
import ch.glauser.validation.unique.Unique;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Model représentant une machine
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Machine extends Model {
    @NotEmpty
    @MaxLength(255)
    private String nom;
    @MaxLength(4096)
    private String description;

    @NotNull
    private Identite proprietaire;

    @Unique
    private List<Piece> pieces;

    @Override
    public Validation validateChild() {
        return Validation.validate(this, Machine.class);
    }
}
