package ch.glauser.gestionstock.adresse.model;

import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.validation.maxlength.MaxLength;
import lombok.Data;

/**
 * Object pour stocker la structure d'une adresse
 */
@Data
public class Adresse {
    @MaxLength(255)
    private String rue;
    @MaxLength(255)
    private String numero;
    @MaxLength(255)
    private Localite localite;
}
