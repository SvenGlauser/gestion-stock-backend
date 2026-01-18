package ch.glauser.filters.filter.api;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
public class FilterCombinaison {
    private List<Filter<?>> filters;
    private Type type;

    public FilterCombinaison() {
        this.filters = new ArrayList<>();
        this.type = Type.AND;
    }

    public static FilterCombinaison and(Collection<Filter<?>> filters) {
        FilterCombinaison combinator = new FilterCombinaison();
        combinator.setFilters(CollectionUtils
                .emptyIfNull(filters)
                .stream()
                .filter(Objects::nonNull)
                .toList());
        combinator.setType(Type.AND);
        return combinator;
    }

    public static FilterCombinaison or(Collection<Filter<?>> filters) {
        FilterCombinaison combinator = new FilterCombinaison();
        combinator.setFilters(CollectionUtils
                .emptyIfNull(filters)
                .stream()
                .filter(Objects::nonNull)
                .toList());
        combinator.setType(Type.OR);
        return combinator;
    }

    public enum Type {
        AND,
        OR
    }
}
