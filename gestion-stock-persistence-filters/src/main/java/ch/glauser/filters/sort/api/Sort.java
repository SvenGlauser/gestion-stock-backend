package ch.glauser.filters.sort.api;

import ch.glauser.filters.api.field.Field;
import ch.glauser.filters.automatic.AutomaticField;
import ch.glauser.filters.utils.JpaUtils;
import ch.glauser.validation.common.Validation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Sort {
    private String field;
    private Direction direction;

    /**
     * Génère l'ordre
     * @return La condition order by
     */
    public org.springframework.data.domain.Sort.Order getOrder() {
        Validation
                .of(this.getClass())
                .validateNotNull(this.getField(), "field")
                .validateNotNull(this.getDirection(), "direction")
                .execute();

        return switch (this.getDirection()) {
            case ASC -> org.springframework.data.domain.Sort.Order.asc(this.getField());
            case DESC -> org.springframework.data.domain.Sort.Order.desc(this.getField());
        };
    }

    /**
     * Génère l'ordre pour les CriteriaBuilder
     * @return La condition order by
     */
    public Order getOrder(Path<?> path, CriteriaBuilder criteriaBuilder) {
        Validation
                .of(this.getClass())
                .validateNotNull(this.getField(), "field")
                .validateNotNull(this.getDirection(), "direction")
                .execute();

        final Path<?> fieldPath = JpaUtils.getPath(path, this.field);

        return switch (this.getDirection()) {
            case ASC -> criteriaBuilder.asc(fieldPath);
            case DESC -> criteriaBuilder.desc(fieldPath);
        };
    }

    /**
     * Génère un ordre à partir d'un champ
     * @param field Champ
     * @param fieldNames Noms du champ
     * @return L'ordre généré
     */
    public static Sort of(Field<?> field, String ...fieldNames) {
        if (Objects.isNull(field) || Objects.isNull(field.getOrder())) {
            return null;
        }

        final String fieldName = String.join(".", fieldNames);

        return switch (Direction.of(field.getOrder())) {
            case ASC -> Sort.asc(fieldName);
            case DESC -> Sort.desc(fieldName);
        };
    }

    /**
     * Génère un ordre à partir d'un champ automatic
     * @param field Champ automatique
     * @return L'ordre généré
     */
    public static Sort of(AutomaticField<?> field) {
        if (Objects.isNull(field) || Objects.isNull(field.getOrder())) {
            return null;
        }

        return switch (Direction.of(field.getOrder())) {
            case ASC -> Sort.asc(field.getField());
            case DESC -> Sort.desc(field.getField());
        };
    }

    /**
     * Génère un ordre croissant
     *
     * @param field Nom du champ séparé par des points pour les champs imbriqués
     *
     * @return L'ordre croissant
     */
    public static Sort asc(String field) {
        Sort sort = new Sort();
        sort.setField(field);
        sort.setDirection(Direction.ASC);
        return sort;
    }

    /**
     * Génère un ordre décroissant
     *
     * @param field Nom du champ séparé par des points pour les champs imbriqués
     *
     * @return L'ordre décroissant
     */
    public static Sort desc(String field) {
        Sort sort = new Sort();
        sort.setField(field);
        sort.setDirection(Direction.DESC);
        return sort;
    }

    /**
     * Types d'ordres possibles
     */
    public enum Direction {
        ASC,
        DESC;

        public static Direction of(Field.Direction direction) {
            return switch (direction) {
                case ASC -> ASC;
                case DESC -> DESC;
            };
        }
    }
}
