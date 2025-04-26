package ch.glauser.gestionstock.common.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FilterCombinator {
    private List<Filter> filters;
    private Type type;

    public static FilterCombinator and(List<Filter> filters) {
        FilterCombinator combinator = new FilterCombinator();
        combinator.setFilters(filters);
        combinator.setType(Type.AND);
        return combinator;
    }

    public static FilterCombinator or(List<Filter> filters) {
        FilterCombinator combinator = new FilterCombinator();
        combinator.setFilters(filters);
        combinator.setType(Type.OR);
        return combinator;
    }

    public enum Type {
        AND,
        OR
    }
}
