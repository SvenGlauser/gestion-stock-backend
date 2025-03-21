package ch.glauser.gestionstock.common.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequest {
    private Integer page;
    private Integer pageSize;
    private List<Filter> filters;
}
