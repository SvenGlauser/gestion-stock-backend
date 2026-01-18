package ch.glauser.filters.utils;

import ch.glauser.filters.api.field.Field;
import ch.glauser.filters.api.search.SearchQuery;
import ch.glauser.filters.filter.api.Filter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@NoArgsConstructor
public class SearchQueryMapperBuilder<T extends SearchQuery> {
    private final List<SearchQueryMapper<T, ?>> mappers = new LinkedList<>();

    public static <T extends SearchQuery> SearchQueryMapperBuilder<T> of() {
        return new SearchQueryMapperBuilder<>();
    }

    public <R> SearchQueryMapperBuilder<T> add(Function<T, Field<R>> fieldGetter,
                                               Supplier<Filter<R>> filterSupplier,
                                               String ...fields) {
        mappers.add(SearchQueryMapper.of(fieldGetter, filterSupplier, fields));
        return this;
    }

    public List<SearchQueryMapper<T, ?>> build() {
        return mappers;
    }
}
