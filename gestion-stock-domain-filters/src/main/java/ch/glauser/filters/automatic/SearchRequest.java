package ch.glauser.filters.automatic;

import ch.glauser.filters.api.search.SearchQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequest extends SearchQuery {
    private List<AutomaticFieldCombinator> combinators;
}
