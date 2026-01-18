package ch.glauser.filters.api.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Field<T> {
    private T value;
    private Direction order;

    public enum Direction {
        ASC,
        DESC
    }
}
