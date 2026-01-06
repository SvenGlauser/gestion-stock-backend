package ch.glauser.validation.minlength;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Génère une exception si la chaîne de caractère n'a pas une longueur suffisante
 * {@link CharSequence}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinLength {
    int value();
}
