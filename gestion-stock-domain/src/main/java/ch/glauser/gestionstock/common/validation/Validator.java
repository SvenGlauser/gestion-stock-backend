package ch.glauser.gestionstock.common.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;

/**
 * Classe de validation
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Validator {

    /**
     * Valide un object
     *
     * @param object Object à valider
     * @param classe Classe à valider
     */
    public static <T> void validate(T object, Class<T> classe) {
        if (Objects.isNull(classe)) {
            throw new ValidationException(new Error("La classe à valider ne doit pas être null", "classe", Object.class));
        }

        if (Objects.isNull(object)) {
            throw new ValidationException(new Error("L'objet à valider ne doit pas être null", "object", classe));
        }

        List<Error> erreurs = validateClass(object, classe);

        if (CollectionUtils.isNotEmpty(erreurs)) {
            throw new ValidationException(erreurs);
        }
    }

    /**
     * Valide une classe
     *
     * @param object Objet à valider
     * @param classe Classe à valider
     * @return Une liste {@link Error}
     */
    private static List<Error> validateClass(Object object, Class<?> classe) {
        if (Objects.isNull(object) || Objects.isNull(classe)) {
            return new ArrayList<>();
        }

        List<Error> erreurs = new ArrayList<>();

        List<Field> annotatedFields = Arrays.stream(classe.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers())).toList();

        List<Field> notNull = getAllFieldsWithSpecificAnnotation(annotatedFields, NotNull.class);
        List<Field> notEmpty = getAllFieldsWithSpecificAnnotation(annotatedFields, NotEmpty.class);
        List<Field> minValue = getAllFieldsWithSpecificAnnotation(annotatedFields, MinValue.class);
        List<Field> maxValue = getAllFieldsWithSpecificAnnotation(annotatedFields, MaxValue.class);
        List<Field> cascadeValidation = getAllFieldsWithSpecificAnnotation(annotatedFields, CascadeValidation.class);

        erreurs.addAll(notNull.stream().map(field -> validateNotNull(object, field)).toList());
        erreurs.addAll(notEmpty.stream().map(field -> validateNotEmpty(object, field)).toList());
        erreurs.addAll(minValue.stream().map(field -> validateMinValue(object, field)).toList());
        erreurs.addAll(maxValue.stream().map(field -> validateMaxValue(object, field)).toList());
        erreurs.addAll(cascadeValidation.stream().map(field -> validateCascade(object, field)).flatMap(List::stream).toList());

        return erreurs.stream().filter(Objects::nonNull).toList();
    }

    /**
     * Valide que le champ n'est pas null
     *
     * @param object Objet à valider
     * @param notNullField Champs à valider
     * @return Une {@link Error} si invalide
     */
    private static Error validateNotNull(Object object, Field notNullField) {
        if (verifyNotCondition(object, notNullField, Objects::nonNull)) {
            return new Error("Le champ ne doit pas être vide", notNullField);
        }

        return null;
    }

    /**
     * Valide que le champ n'est pas une chaîne de caractères vide ou avec uniquement des espaces (blank)
     *
     * @param object Objet à valider
     * @param notEmptyField Champs à valider
     * @return Une {@link Error} si invalide
     */
    private static Error validateNotEmpty(Object object, Field notEmptyField) {
        if (verifyNotCondition(object, notEmptyField, objectToTest -> {
            if (objectToTest instanceof String string) {
                return StringUtils.isNotBlank(string);
            }

            if (objectToTest instanceof Collection<?> collection) {
                return CollectionUtils.isNotEmpty(collection);
            }

            return false;
        })) {
            return new Error("Le champ ne doit pas être vide", notEmptyField);
        }

        return null;
    }

    /**
     * Valide que le champ est supérieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param minValueField Champ à valider
     * @return Une {@link Error} si invalide
     */
    private static Error validateMinValue(Object object, Field minValueField) {
        double minValue = minValueField.getAnnotation(MinValue.class).value();

        if (verifyNotCondition(object, minValueField, objectToTest -> {
            if (objectToTest instanceof Number number) {
                return number.doubleValue() > minValue;
            }

            return false;
        })) {
            return new Error("La valeur du champ doit être supérieur à " + minValue, minValueField);
        }

        return null;
    }

    /**
     * Valide que le champ est inférieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param minValueField Champ à valider
     * @return Une {@link Error} si invalide
     */
    private static Error validateMaxValue(Object object, Field minValueField) {
        double maxValue = minValueField.getAnnotation(MaxValue.class).value();

        if (verifyNotCondition(object, minValueField, objectToTest -> {
            if (objectToTest instanceof Number number) {
                return number.doubleValue() < maxValue;
            }

            return false;
        })) {
            return new Error("La valeur du champ doit être inférieur à " + maxValue, minValueField);
        }

        return null;
    }

    /**
     * Valide le champ en cascade
     *
     * @param object Objet à valider
     * @param cascadeValidationField Champ à valider en cascade
     * @return Une liste {@link Error}
     */
    private static List<Error> validateCascade(Object object, Field cascadeValidationField) {
        Object value = getValue(object, cascadeValidationField);

        // Dans le cas ou l'objet se contient lui-même, on évite un StackOverflowError
        if (object == value) {
            return List.of();
        }

        return validateClass(value, cascadeValidationField.getType());
    }

    /**
     * Vérifie que la condition est respectée
     *
     * @param object Objet à vérifier
     * @param field Champ
     * @param condition Condition à vérifier
     * @return {@code true} si invalide, {@code false} si valide
     */
    private static boolean verifyNotCondition(Object object, Field field, Predicate<Object> condition) {
        Object value = getValue(object, field);
        return !condition.test(value);
    }

    /**
     * Récupère la valeur stockée dans le champ
     *
     * @param object Objet
     * @param field Champ à récupérer
     * @return La valeur du champ
     */
    @SuppressWarnings("java:S3011")
    private static Object getValue(Object object, Field field) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new TechnicalException("L'accès au champs est impossible : " + field.getDeclaringClass().getName() + "." + field.getName(), e);
        }
    }

    /**
     * Récupère tous les champs qui ont l'annotation recherchée
     *
     * @param annotatedFields Listes des champs
     * @param annotation Annotation à rechercher
     * @return Une liste de {@link Field}
     */
    private static List<Field> getAllFieldsWithSpecificAnnotation(List<Field> annotatedFields, Class<? extends Annotation> annotation) {
        return annotatedFields.stream().filter(field -> field.isAnnotationPresent(annotation)).toList();
    }
}
