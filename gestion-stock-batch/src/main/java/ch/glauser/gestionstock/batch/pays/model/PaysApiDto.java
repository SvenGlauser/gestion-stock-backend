package ch.glauser.gestionstock.batch.pays.model;

import lombok.Data;

import java.util.List;

/**
 * Pays récupéré via l'API
 */
@Data
public class PaysApiDto {
    // Geographic information
    private Double area;
    private Maps maps;
    private List<Double> latlng;
    private String region;
    private String subregion;
    private List<String> borders;
    private List<String> timezones;

    // Flag
    private String flag;
    private Flags flags;

    // Names
    private String name;
    private Translations translations;
    private List<String> altSpellings;

    // Acronyms
    private String cioc;
    private String alpha2Code;
    private String alpha3Code;
    private String nativeName;

    // Country information
    private String capital;
    private String demonym;
    private List<Language> languages;
    private List<Currency> currencies;
    private Integer population;
    private Boolean independent;
    private List<RegionalBloc> regionalBlocs;
    private List<String> topLevelDomain;
    private Double populationDensity;
    private Double gini;

    // IDK
    private String numericCode;
    private List<String> callingCodes;

    /**
     * Monnaie récupérée via l'API
     */
    @Data
    public static class Currency {
        private String code;
        private String name;
        private String symbol;
    }

    /**
     * Drapeau récupéré via l'API
     */
    @Data
    public static class Flags {
        private String png;
        private String svg;
    }

    /**
     * Langue récupérée via l'API
     */
    @Data
    public static class Language {
        private String name;
        private String iso639_1;
        private String iso639_2;
        private String nativeName;
    }

    /**
     * Informations de carte récupérées via l'API
     */
    @Data
    public static class Maps {
        private String googleMaps;
        private String openStreetMaps;
    }

    /**
     * Bloc régional récupéré via l'API
     */
    @Data
    public static class RegionalBloc {
        private String name;
        private String acronym;
        private List<String> otherNames;
        private List<String> otherAcronyms;
    }

    /**
     * Traductions récupérées via l'API
     */
    @Data
    public static class Translations {
        private String br;
        private String de;
        private String es;
        private String fa;
        private String fr;
        private String hr;
        private String hu;
        private String it;
        private String ja;
        private String nl;
        private String pt;
    }
}
