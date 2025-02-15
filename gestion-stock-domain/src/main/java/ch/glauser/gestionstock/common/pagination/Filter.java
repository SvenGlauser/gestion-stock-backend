package ch.glauser.gestionstock.common.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Filter {
    private String field;
    private Object value;
    private Order order;

    public enum Order {
        ASC,
        DESC
    }
}
