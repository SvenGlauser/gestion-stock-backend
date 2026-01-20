package ch.glauser.filters.filter.api;

import ch.glauser.filters.field.api.Field;
import ch.glauser.filters.utils.JpaUtils;
import ch.glauser.validation.common.Validation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public abstract class Filter<T> {
    private String field;
    private T value;

    protected static <T, R extends Filter<T>> R of(R filter,
                                                   Field<T> field,
                                                   String ...fieldNames) {

        final String fieldName = String.join(".", fieldNames);

        if (StringUtils.isBlank(fieldName) || Objects.isNull(filter) || Objects.isNull(field)) {
            return null;
        }

        filter.setField(fieldName);
        filter.setValue(field.getValue());

        return filter;
    }

    /**
     * Génère le Predicate
     * @param rootPath Chemin racine
     * @param criteriaBuilder CriteriaBuilder
     * @return La condition where
     */
    public Predicate getPredicate(Path<?> rootPath, CriteriaBuilder criteriaBuilder) {
        Validation
                .of(this.getClass())
                .validateNotNull(rootPath, "rootPath")
                .validateNotNull(criteriaBuilder, "criteriaBuilder")
                .validateNotNull(this.getField(), "field")
                .validateNotNull(this.getValue(), "value")
                .execute();
        Path<?> fieldPath = JpaUtils.getPath(rootPath, this.field);

        return this.getPredicateChild(criteriaBuilder, fieldPath);
    }

    protected abstract Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath);
}
