package ch.glauser.filters.filter.api;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.utils.JpaUtils;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Supplier;

/**
 * Filtre de recherche abstrait
 * @param <T> Type de données gérées par le filtre
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class Filter<T> {
    private String field;
    private T value;

    /**
     * Vérifie si la valeur contenue dans le champ {@code value} est considérée comme vide
     * @return Renvoie {@code true} si le filtre est considéré comme vide
     */
    public abstract boolean isEmpty();

    /**
     * Vérifie si la valeur contenue dans le champ {@code value} n'est pas considérée comme vide
     * @return Renvoie {@code true} si le filtre n'est pas considérée comme vide
     */
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Valide la classe enfant
     * @return L'instance de validation
     */
    protected abstract Validation validateChild();

    /**
     * Génère le Predicate
     * @param rootPath Chemin racine
     * @param criteriaBuilder CriteriaBuilder
     * @return La condition where
     */
    public Predicate getPredicate(Path<?> rootPath,
                                  CriteriaBuilder criteriaBuilder) throws ValidationException {
        this.validateChild()
                .validateNotNull(rootPath, "rootPath")
                .validateNotNull(criteriaBuilder, "criteriaBuilder")
                .validateNotNull(this.field, "field")
                .execute();

        Path<?> fieldPath = JpaUtils.getPath(rootPath, this.field);

        return this.getPredicateChild(criteriaBuilder, fieldPath);
    }

    /**
     * Génère le Predicate
     * @param fieldPath Chemin du champ
     * @param criteriaBuilder CriteriaBuilder
     * @return La condition where
     */
    protected abstract Predicate getPredicateChild(CriteriaBuilder criteriaBuilder, Path<?> fieldPath);

    /**
     * Création d'un filtre
     * @param filterConstructor Constructeur du filtre enfant
     * @param searchField Champ sur lequel est basé le filtre
     * @param fieldNames Nom des champs en cascade de l'entité
     * @return Le filtre
     * @param <T> Type de données
     * @param <R> Type de filtre
     * @throws ValidationException Si les paramètres sont nuls
     */
    protected static <T, R extends Filter<T>> R of(Supplier<R> filterConstructor,
                                                   SearchField<T> searchField,
                                                   String ...fieldNames) throws ValidationException {

        final String fieldName = String.join(".", fieldNames);

        Validation
                .of(Filter.class)
                .validateNotNull(filterConstructor, "filterConstructor")
                .validateNotNull(searchField, "searchField")
                .validateNotNull(fieldNames, "fieldNames")
                .validateNotEmpty(fieldName, "fieldName")
                .execute();

        R filter = filterConstructor.get();
        filter.setField(fieldName);
        filter.setValue(searchField.getValue());

        return filter;
    }
}
