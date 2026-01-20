package ch.glauser.filters.searchquery.utils;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.filters.search.api.SearchQuery;
import ch.glauser.validation.common.Validation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Classe contenant les informations de transformation d'un champ de SearchQuery en un filtre
 * @param fieldGetter Récupération du champ de la SearchQuery
 * @param filterSupplier Constructeur du filtre
 * @param fieldsName Liste des champs en cascade
 * @param <T> Le type de la SearchQuery
 * @param <R> Le type des données de filtres
 */
public record SearchQueryMapper<T extends SearchQuery, R>(Function<T, SearchField<R>> fieldGetter,
                                                          Supplier<Filter<R>> filterSupplier,
                                                          List<String> fieldsName) {

    /**
     * Instanciation d'une classe contenant les informations de transformation
     * d'un champ de SearchQuery en un filtre
     *
     * @param fieldGetter Récupération du champ de la SearchQuery
     * @param filterSupplier Constructeur du filtre
     * @param fieldsName Liste des champs en cascade
     * @return Une instance de la classe
     * @param <T> Le type de la SearchQuery
     * @param <R> Le type des données de filtres
     */
    public static <T extends SearchQuery, R> SearchQueryMapper<T, R> of(Function<T, SearchField<R>> fieldGetter,
                                                                        Supplier<Filter<R>> filterSupplier,
                                                                        String... fieldsName) {
        Validation
                .of(SearchQueryMapper.class)
                .validateNotNull(fieldGetter, "fieldGetter")
                .validateNotNull(filterSupplier, "filterSupplier")
                .validateNotNull(fieldsName, "fieldsName")
                .execute();

        return new SearchQueryMapper<>(
                fieldGetter,
                filterSupplier,
                Arrays.asList(fieldsName)
        );
    }
}
