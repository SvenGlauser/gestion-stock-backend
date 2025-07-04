package ch.glauser.gestionstock.piece.model;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.minvalue.MinValue;
import ch.glauser.gestionstock.common.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.common.validation.notnull.NotNull;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant une pièce
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Piece extends Model {
    @NotEmpty
    private String numeroInventaire;

    @NotEmpty
    private String nom;
    private String description;

    @NotNull
    private Fournisseur fournisseur;

    @NotNull
    private Categorie categorie;

    @MinValue(0)
    private Long quantite;
    @MinValue(0)
    private Double prix;

    @Override
    public Validation validateChild() {
        return Validation.validate(this, Piece.class);
    }
}
