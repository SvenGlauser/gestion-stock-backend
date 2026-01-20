package ch.glauser.filters.automatic;

import ch.glauser.filters.search.api.PageableSearchQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Search query de champs de recherche automatiques
 */
@Getter
@Setter
@NoArgsConstructor
public class AutomaticSearchQuery implements PageableSearchQuery {
    private Integer page;
    private Integer pageSize;
    private List<AutomaticSearchFieldCombinaison> combinators;
}
