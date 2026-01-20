package ch.glauser.filters.field.api;

import ch.glauser.filters.sort.api.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
public class Field<T> {
    private T value;
    private Direction order;

    public <R> Field<R> cast(Function<T, R> mapper) {
        Field<R> field = new Field<>();
        field.setValue(mapper.apply(this.value));
        field.setOrder(this.order);
        return field;
    }

}
