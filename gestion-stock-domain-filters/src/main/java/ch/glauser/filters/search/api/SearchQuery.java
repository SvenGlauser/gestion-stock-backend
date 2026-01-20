package ch.glauser.filters.search.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SearchQuery {
    private Integer page;
    private Integer pageSize;
}
