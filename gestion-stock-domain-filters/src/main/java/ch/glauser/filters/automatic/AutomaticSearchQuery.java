package ch.glauser.filters.automatic;

import ch.glauser.filters.search.api.PageableSearchQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Search query de champs de recherche automatiques
 */
@Getter
@Setter
public class AutomaticSearchQuery implements PageableSearchQuery {
    private Integer page;
    private Integer pageSize;
    private List<AutomaticSearchFieldCombinaison> combinators;

    /**
     * Instanciation d'une {@link AutomaticSearchQuery}
     */
    public AutomaticSearchQuery() {
        this.combinators = new ArrayList<>();
    }
}
