package ch.glauser.filters.automatic;

import ch.glauser.filters.api.field.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutomaticField<T> extends Field<T> {
    private String field;
    private Type type;

    public enum Type {
        EQUAL,
        STRING_LIKE
    }
}
