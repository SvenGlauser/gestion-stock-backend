package ch.glauser.filters.filter.charsequence;

import ch.glauser.filters.api.field.Field;
import ch.glauser.filters.filter.api.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FilterLike<T extends CharSequence> extends Filter<T> {

    public static <T extends CharSequence> FilterLike<T> of(Field<T> field, String ...fieldNames) {
        return Filter.of(new FilterLike<>(), field, fieldNames);
    }

    @Override
    protected Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(fieldPath.as(String.class)),
                ("%" + this.getValue() + "%").toLowerCase()
        );
    }
}
