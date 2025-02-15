package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.NotEmpty;
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

    private List<Piece> pieces;
}
