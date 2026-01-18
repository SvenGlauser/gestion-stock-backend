package ch.glauser.filters.utils;

import ch.glauser.filters.api.field.Field;
import ch.glauser.filters.api.search.SearchQuery;
import ch.glauser.filters.filter.api.Filter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public class SearchQueryMapper<T extends SearchQuery, R> {
    private final Function<T, Field<R>> fieldGetter;
    private final Supplier<Filter<R>> filterSupplier;
    private final List<String> fields;

    public static <T extends SearchQuery, R> SearchQueryMapper<T, R> of(Function<T, Field<R>> fieldGetter,
                                                                        Supplier<Filter<R>> filterSupplier,
                                                                        String ...fields) {
        return new SearchQueryMapper<>(
            fieldGetter,
            filterSupplier,
            Arrays.asList(fields)
        );
    }
}
