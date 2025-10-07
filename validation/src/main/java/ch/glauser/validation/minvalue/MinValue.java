package ch.glauser.validation.minvalue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Génère une exception si la valeur n'a pas une valeur suffisante
 * {@link Number}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinValue {
    double value();
}
