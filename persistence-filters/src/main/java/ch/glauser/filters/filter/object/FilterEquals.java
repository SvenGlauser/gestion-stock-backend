package ch.glauser.filters.filter.object;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Filtre de recherche de type EQUALS.
 * Ne gère pas les conditions d'égalité sur des {@code null}
 * @param <T> Type de données
 */
@NoArgsConstructor
public class FilterEquals<T> extends Filter<T> {

    /**
     * Création d'un filtre de type EQUALS
     * @param searchField Champ sur lequel est basé le filtre
     * @param fieldNames Nom des champs en cascade de l'entité
     * @return Le filtre
     * @param <T> Type de données
     * @throws ValidationException Si les paramètres sont nuls
     */
    public static <T> FilterEquals<T> of(SearchField<T> searchField, String ...fieldNames) throws ValidationException {
        return Filter.of(FilterEquals::new, searchField, fieldNames);
    }

    @Override
    public boolean isEmpty() {
        return Objects.isNull(getValue());
    }

    @Override
    protected Validation validateChild() {
        return Validation
                .of(this.getClass())
                .validateNotNull(this.getValue(), "value");
    }

    protected Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath) {
        return criteriaBuilder.equal(fieldPath, this.getValue());
    }
}
