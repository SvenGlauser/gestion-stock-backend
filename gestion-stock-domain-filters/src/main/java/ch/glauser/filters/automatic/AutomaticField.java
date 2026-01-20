package ch.glauser.filters.automatic;

import ch.glauser.filters.field.api.Field;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutomaticField<T> extends Field<T> {
    private String field;
    private Type type;

    public AutomaticField() {
        this.type = Type.EQUAL;
    }

    public enum Type {
        EQUAL,
        STRING_LIKE
    }
}
