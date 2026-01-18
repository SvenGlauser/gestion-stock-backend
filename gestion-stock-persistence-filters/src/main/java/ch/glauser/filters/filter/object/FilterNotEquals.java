package ch.glauser.filters.filter.object;

import ch.glauser.filters.api.field.Field;
import ch.glauser.filters.filter.api.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FilterNotEquals<T> extends Filter<T> {

    public static <T> FilterNotEquals<T> of(Field<T> field, String ...fieldNames) {
        return Filter.of(new FilterNotEquals<>(), field, fieldNames);
    }

    protected Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath) {
        return criteriaBuilder.notEqual(fieldPath, this.getValue());
    }
}
