package ch.glauser.gestionstock.batch.localite.model;

import lombok.Data;

/**
 * Localité récupérée via l'API
 */
@Data
public class LocaliteApiDto {
    private String name;
    private String postalCode;
    private Commune commune;
    private District district;
    private CantonApiDto canton;

    /**
     * Commune récupérée via l'API
     */
    @Data
    public static class Commune {
        private Integer key;
        private String name;
        private String shortName;
    }

    /**
     * District récupéré via l'API
     */
    @Data
    public static class District {
        private Integer key;
        private String name;
        private String shortName;
    }
}
