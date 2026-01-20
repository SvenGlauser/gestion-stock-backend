package ch.glauser.filters.filter.charsequence;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.validation.exception.ValidationException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

/**
 * Filtre de recherche de type LIKE
 * @param <T> Type de données
 */
@NoArgsConstructor
public class FilterLike<T extends CharSequence> extends Filter<T> {

    /**
     * Création d'un filtre de type LIKE
     * @param searchField Champ sur lequel est basé le filtre
     * @param fieldNames Nom des champs en cascade de l'entité
     * @return Le filtre
     * @param <T> Type de données
     * @throws ValidationException Si les paramètres sont nuls
     */
    public static <T extends CharSequence> FilterLike<T> of(SearchField<T> searchField, String ...fieldNames) throws ValidationException {
        return Filter.of(FilterLike::new, searchField, fieldNames);
    }

    @Override
    protected Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(fieldPath.as(String.class)),
                ("%" + this.getValue() + "%").toLowerCase()
        );
    }
}
