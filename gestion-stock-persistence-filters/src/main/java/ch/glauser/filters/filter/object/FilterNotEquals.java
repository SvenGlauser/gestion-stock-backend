package ch.glauser.filters.filter.object;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.validation.exception.ValidationException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

/**
 * Filtre de recherche de type NOT EQUALS
 * @param <T> Type de données
 */
@NoArgsConstructor
public class FilterNotEquals<T> extends Filter<T> {

    /**
     * Création d'un filtre de type NOT EQUALS
     * @param searchField Champ sur lequel est basé le filtre
     * @param fieldNames Nom des champs en cascade de l'entité
     * @return Le filtre
     * @param <T> Type de données
     * @throws ValidationException Si les paramètres sont nuls
     */
    public static <T> FilterNotEquals<T> of(SearchField<T> searchField, String ...fieldNames) throws ValidationException {
        return Filter.of(FilterNotEquals::new, searchField, fieldNames);
    }

    protected Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath) {
        return criteriaBuilder.notEqual(fieldPath, this.getValue());
    }
}
