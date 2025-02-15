package ch.glauser.gestionstock.piece.model;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.NotEmpty;
import ch.glauser.gestionstock.common.validation.NotNull;
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

    private String numeroFournisseur;
    private Fournisseur fournisseur;

    @NotNull
    private Categorie categorie;

    @NotNull
    private Long quantite;
    @NotNull
    private Double prix;
}
