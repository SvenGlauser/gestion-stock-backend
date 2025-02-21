package ch.glauser.gestionstock.common.validation.notempty;

import ch.glauser.gestionstock.common.validation.notnull.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

/**
 * Génère une exception si la valeur est vide
 * Vérifie également l'annotation {@link NotNull}
 *
 * {@link Collection}
 * {@link String}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotEmpty {
}
