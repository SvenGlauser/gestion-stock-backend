package ch.glauser.filters.automatic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AutomaticFieldCombinator {
    private List<AutomaticField<?>> filters;
    private Type type;

    public static AutomaticFieldCombinator and(List<AutomaticField<?>> filters) {
        AutomaticFieldCombinator combinator = new AutomaticFieldCombinator();
        combinator.setFilters(filters);
        combinator.setType(Type.AND);
        return combinator;
    }

    public static AutomaticFieldCombinator or(List<AutomaticField<?>> filters) {
        AutomaticFieldCombinator combinator = new AutomaticFieldCombinator();
        combinator.setFilters(filters);
        combinator.setType(Type.OR);
        return combinator;
    }

    public enum Type {
        AND,
        OR
    }
}
