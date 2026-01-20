package ch.glauser.filters.search.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Search query abstraite conçue pour les recherches sur des <b>champs non automatiques</b>
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class SearchQuery implements PageableSearchQuery {
    private Integer page;
    private Integer pageSize;
}
