package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.validation.notnull.NotNull;
import ch.glauser.gestionstock.validation.unique.Unique;
import ch.glauser.gestionstock.identite.model.Identite;
import ch.glauser.gestionstock.piece.model.Piece;
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
    private String nom;
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
