package ch.glauser.filters.automatic;

import ch.glauser.filters.field.api.SearchField;
import lombok.Getter;
import lombok.Setter;

/**
 * Champ de recherche automatic qui gère les filtres et les tris
 * @param <T> Type de données dans le champ
 */
@Getter
@Setter
public class AutomaticSearchField<T> extends SearchField<T> {
    private String field;
    private Type type;

    /**
     * Construit un nouveau champ de recherche
     */
    public AutomaticSearchField() {
        // FIXME SVG retirer ca et gérer le cas dans les utilitaires
        this.type = Type.EQUAL;
    }

    /**
     * Type de filtrage
     */
    public enum Type {
        EQUAL,
        STRING_LIKE
    }
}
