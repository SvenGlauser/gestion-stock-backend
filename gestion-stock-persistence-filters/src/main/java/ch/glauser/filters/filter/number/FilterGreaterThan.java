package ch.glauser.filters.filter.number;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.validation.exception.ValidationException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

/**
 * Filtre de recherche de type GREATER THAN
 * @param <T> Type de données
 */
@NoArgsConstructor
public class FilterGreaterThan<T extends Comparable<T>> extends Filter<T> {

    /**
     * Création d'un filtre de type GREATER THAN
     * @param searchField Champ sur lequel est basé le filtre
     * @param fieldNames Nom des champs en cascade de l'entité
     * @return Le filtre
     * @param <T> Type de données
     * @throws ValidationException Si les paramètres sont nuls
     */
    public static <T extends Comparable<T>> FilterGreaterThan<T> of(SearchField<T> searchField, String ...fieldNames) throws ValidationException {
        return Filter.of(FilterGreaterThan::new, searchField, fieldNames);
    }

    @Override
    protected Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath) {
        return criteriaBuilder.greaterThan(
                (Expression<T>) fieldPath,
                this.getValue()
        );
    }
}
