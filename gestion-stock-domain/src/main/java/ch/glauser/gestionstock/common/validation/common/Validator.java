package ch.glauser.gestionstock.common.validation.common;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class de validation des annotations
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Validator {

    protected final Validation validation;

    /**
     * Valide tous les champs enfants de l'objet pour cette annotation
     * @param object Objet à valider
     */
    public void validate(Object object) {
        if (Objects.isNull(object)) {
            return;
        }

        Class<?> classToValidate = object.getClass();

        while (Objects.nonNull(classToValidate) && !Object.class.equals(classToValidate)) {
            Arrays.stream(classToValidate.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .filter(field -> field.isAnnotationPresent(this.getAnnotationClass()))
                    .forEach(field -> this.validate(object, field));

            classToValidate = classToValidate.getSuperclass();
        }
    }

    /**
     * Validation de l'objet grâce au champ
     * @param object Objet à valider
     * @param field Champ
     */
    public abstract void validate(Object object, Field field);

    /**
     * Renvoie la classe de l'annotation
     */
    protected abstract Class<? extends Annotation> getAnnotationClass();
}
