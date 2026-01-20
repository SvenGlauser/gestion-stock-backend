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
    private CombinaisonType type;

    public FilterCombinaison() {
        this.filters = new ArrayList<>();
        this.type = CombinaisonType.AND;
    }

    public static FilterCombinaison and(Collection<Filter<?>> filters) {
        FilterCombinaison combinator = new FilterCombinaison();
        combinator.setFilters(CollectionUtils
                .emptyIfNull(filters)
                .stream()
                .filter(Objects::nonNull)
                .toList());
        combinator.setType(CombinaisonType.AND);
        return combinator;
    }

    public static FilterCombinaison or(Collection<Filter<?>> filters) {
        FilterCombinaison combinator = new FilterCombinaison();
        combinator.setFilters(CollectionUtils
                .emptyIfNull(filters)
                .stream()
                .filter(Objects::nonNull)
                .toList());
        combinator.setType(CombinaisonType.OR);
        return combinator;
    }
}
