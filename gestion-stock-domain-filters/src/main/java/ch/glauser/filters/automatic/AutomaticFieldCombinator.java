package ch.glauser.filters.automatic;

import ch.glauser.filters.filter.api.CombinaisonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AutomaticFieldCombinator {
    private List<AutomaticField<?>> filters;
    private CombinaisonType type;

    public static AutomaticFieldCombinator and(List<AutomaticField<?>> filters) {
        AutomaticFieldCombinator combinator = new AutomaticFieldCombinator();
        combinator.setFilters(filters);
        combinator.setType(CombinaisonType.AND);
        return combinator;
    }

    public static AutomaticFieldCombinator or(List<AutomaticField<?>> filters) {
        AutomaticFieldCombinator combinator = new AutomaticFieldCombinator();
        combinator.setFilters(filters);
        combinator.setType(CombinaisonType.OR);
        return combinator;
    }
}
