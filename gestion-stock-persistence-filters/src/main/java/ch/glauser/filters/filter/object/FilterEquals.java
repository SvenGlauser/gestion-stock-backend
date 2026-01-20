package ch.glauser.filters.filter.object;

import ch.glauser.filters.field.api.Field;
import ch.glauser.filters.filter.api.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FilterEquals<T> extends Filter<T> {

    public static <T> FilterEquals<T> of(Field<T> field, String ...fieldNames) {
        return Filter.of(new FilterEquals<>(), field, fieldNames);
    }

    protected Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath) {
        return criteriaBuilder.equal(fieldPath, this.getValue());
    }
}
