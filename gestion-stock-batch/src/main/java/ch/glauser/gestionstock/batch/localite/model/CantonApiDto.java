package ch.glauser.gestionstock.batch.localite.model;

import lombok.Data;

/**
 * Canton récupéré via l'API
 */
@Data
public class CantonApiDto {
    private Integer key;
    private Integer historicalCode;
    private String name;
    private String shortName;
}
