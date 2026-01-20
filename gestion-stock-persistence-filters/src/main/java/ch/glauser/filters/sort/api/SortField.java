package ch.glauser.filters.sort.api;

import ch.glauser.filters.automatic.AutomaticSearchField;
import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.utils.JpaUtils;
import ch.glauser.validation.common.Validation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Objects;

/**
 * Champ trié d'une requête
 */
@Getter
@Setter
@NoArgsConstructor
public class SortField {
    private String field;
    private Direction direction;

    /**
     * Génère l'ordre
     * @return La condition order by
     */
    public Sort.Order getJpaPaginationOrder() {
        Validation
                .of(this.getClass())
                .validateNotNull(this.getField(), "field")
                .validateNotNull(this.getDirection(), "direction")
                .execute();

        return switch (this.getDirection()) {
            case ASC -> Sort.Order.asc(this.getField());
            case DESC -> Sort.Order.desc(this.getField());
        };
    }

    /**
     * Génère l'ordre pour les CriteriaBuilder
     * @param path Chemin racine
     * @param criteriaBuilder CriteriaBuilder
     * @return La condition pour le order by
     */
    public Order getCriteriaBuilderOrder(Path<?> path, CriteriaBuilder criteriaBuilder) {
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
     * @param searchField Champ
     * @param fieldNames Noms du champ
     * @return L'ordre généré
     */
    public static SortField of(SearchField<?> searchField, String ...fieldNames) {
        if (Objects.isNull(searchField) || Objects.isNull(searchField.getOrder())) {
            return null;
        }

        final String fieldName = String.join(".", fieldNames);

        return SortField.of(fieldName, searchField.getOrder());
    }

    /**
     * Génère un ordre à partir d'un champ automatic
     * @param field Champ automatique
     * @return L'ordre généré
     */
    public static SortField of(AutomaticSearchField<?> field) {
        if (Objects.isNull(field) || Objects.isNull(field.getOrder())) {
            return null;
        }

        return SortField.of(field.getField(), field.getOrder());
    }

    /**
     * Génère un ordre
     *
     * @param field Nom du champ séparé par des points pour les champs imbriqués
     * @param direction Sens de tri
     *
     * @return L'ordre
     */
    public static SortField of(String field, Direction direction) {
        SortField sortField = new SortField();
        sortField.setField(field);
        sortField.setDirection(direction);
        return sortField;
    }

    /**
     * Génère un ordre croissant
     *
     * @param field Nom du champ séparé par des points pour les champs imbriqués
     *
     * @return L'ordre croissant
     */
    public static SortField asc(String field) {
        SortField sortField = new SortField();
        sortField.setField(field);
        sortField.setDirection(Direction.ASC);
        return sortField;
    }

    /**
     * Génère un ordre décroissant
     *
     * @param field Nom du champ séparé par des points pour les champs imbriqués
     *
     * @return L'ordre décroissant
     */
    public static SortField desc(String field) {
        SortField sortField = new SortField();
        sortField.setField(field);
        sortField.setDirection(Direction.DESC);
        return sortField;
    }
}
