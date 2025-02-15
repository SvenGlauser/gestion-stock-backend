package ch.glauser.gestionstock.adresse.model;

import ch.glauser.gestionstock.localite.model.Localite;
import lombok.Data;

/**
 * Object pour stocker la structure d'une adresse
 */
@Data
public class Adresse {
    private String rue;
    private String numero;
    private Localite localite;
}
