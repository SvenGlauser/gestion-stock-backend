package ch.glauser.filters.searchquery.utils;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.filters.searchquery.api.SearchQuery;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Builder de {@link SearchQueryMapper}
 * @param <T> Type de SearchQuery
 */
public class SearchQueryMapperBuilder<T extends SearchQuery> {
    private final List<SearchQueryMapper<T, ?>> mappers;

    private SearchQueryMapperBuilder() {
        this.mappers = new LinkedList<>();
    }

    /**
     * Ajoute un {@link SearchQueryMapper} à la liste du builder
     * @param fieldGetter Méthode d'accès au champ
     * @param filterSupplier Constructeur de filtre
     * @param fields Nom des champs en cascade
     * @return L'instance du builder
     * @param <R> Le type de données de filtres
     */
    public <R> SearchQueryMapperBuilder<T> add(Function<T, SearchField<R>> fieldGetter,
                                               Supplier<Filter<R>> filterSupplier,
                                               String ...fields) {
        mappers.add(SearchQueryMapper.of(fieldGetter, filterSupplier, fields));
        return this;
    }

    /**
     * Renvoie une liste de mappers
     * @return Une liste de mappers
     */
    public List<SearchQueryMapper<T, ?>> build() {
        return mappers;
    }

    /**
     * Instanciation d'un builder
     * @return Une nouveau builder
     * @param <T> Le type de la SearchQuery
     */
    public static <T extends SearchQuery> SearchQueryMapperBuilder<T> of() {
        return new SearchQueryMapperBuilder<>();
    }
}
