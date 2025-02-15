package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.NotEmpty;
import ch.glauser.gestionstock.common.validation.NotNull;
import ch.glauser.gestionstock.common.validation.Validator;
import ch.glauser.gestionstock.contact.model.Contact;
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
    private Contact contact;

    private List<Piece> pieces;

    @Override
    public void validate() {
        Validator.validate(this, Machine.class);
    }
}
