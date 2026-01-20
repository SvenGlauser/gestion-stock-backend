package ch.glauser.filters.filter.number;

import ch.glauser.filters.field.api.Field;
import ch.glauser.filters.filter.api.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FilterGreaterThan<T extends Comparable<T>> extends Filter<T> {

    public static <T extends Comparable<T>> FilterGreaterThan<T> of(Field<T> field, String ...fieldNames) {
        return Filter.of(new FilterGreaterThan<>(), field, fieldNames);
    }

    @Override
    protected Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath) {
        return criteriaBuilder.greaterThan(
                (Expression<T>) fieldPath,
                this.getValue()
        );
    }
}
