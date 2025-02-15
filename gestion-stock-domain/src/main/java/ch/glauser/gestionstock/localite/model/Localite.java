package ch.glauser.gestionstock.localite.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.validation.NotEmpty;
import ch.glauser.gestionstock.common.validation.NotNull;
import ch.glauser.gestionstock.pays.model.Pays;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model représentant une localité
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Localite extends Model {
    @NotEmpty
    private String nom;
    @NotEmpty
    private String npa;
    @NotNull
    private Pays pays;
}
