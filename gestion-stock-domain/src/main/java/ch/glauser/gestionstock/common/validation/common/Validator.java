package ch.glauser.gestionstock.common.validation.common;

import ch.glauser.gestionstock.common.validation.cascade.CascadeValidation;
import ch.glauser.gestionstock.common.validation.exception.TechnicalException;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.common.validation.maxvalue.MaxValue;
import ch.glauser.gestionstock.common.validation.minvalue.MinValue;
import ch.glauser.gestionstock.common.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.common.validation.notnull.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Classe de validation
 */
public final class Validator {

    private final Class<?> classe;
    private final List<Error> errors = new ArrayList<>();

    /**
     * Constructeur
     * @param classe Classe en cours de validation
     */
    private Validator(Class<?> classe) {
        if (Objects.isNull(classe)) {
            throw new ValidationException(new Error("La classe à valider ne doit pas être null", "classe", Object.class));
        }

        this.classe = classe;
    }

    /**
     * Récupérer une isntance de validateur
     *
     * @param classe Classe à valider
     * @return L'instance de validation
     */
    public static Validator of(Class<?> classe) {
        return new Validator(classe);
    }

    /**
     * Valide un object
     *
     * @param object Object à valider
     * @param classe Classe à valider
     * @return L'instance de validation
     */
    public static <T> Validator validate(T object, Class<T> classe) {
        if (Objects.isNull(object)) {
            throw new ValidationException(new Error("L'objet à valider ne doit pas être null", "object", classe));
        }

        Validator validator = new Validator(classe);
        validator.validateWithoutNullCheck(object, classe);

        return validator;
    }

    /**
     * Valide une classe
     *
     * @param object Objet à valider
     * @param classe Classe à valider
     */
    private void validateWithoutNullCheck(Object object, Class<?> classe) {
        if (Objects.isNull(object)) {
            return;
        }

        Validator validator = new Validator(classe);

        List<Field> annotatedFields = Arrays.stream(classe.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers())).toList();

        List<Field> notNull = getAllFieldsWithSpecificAnnotation(annotatedFields, NotNull.class);
        List<Field> notEmpty = getAllFieldsWithSpecificAnnotation(annotatedFields, NotEmpty.class);
        List<Field> minValue = getAllFieldsWithSpecificAnnotation(annotatedFields, MinValue.class);
        List<Field> maxValue = getAllFieldsWithSpecificAnnotation(annotatedFields, MaxValue.class);
        List<Field> cascadeValidation = getAllFieldsWithSpecificAnnotation(annotatedFields, CascadeValidation.class);

        notNull.forEach(field -> validator.validateNotNull(object, field));
        notEmpty.forEach(field -> validator.validateNotEmpty(object, field));
        minValue.forEach(field -> validator.validateMinValue(object, field));
        maxValue.forEach(field -> validator.validateMaxValue(object, field));
        cascadeValidation.forEach(field -> validator.validateCascade(object, field));

        // Regroupe les erreurs de validation dans la classe parente
        this.errors.addAll(validator.errors);
    }

    /**
     * Valide que le champ n'est pas null
     *
     * @param object Objet à valider
     * @param notNullField Champ à valider
     */
    private void validateNotNull(Object object, Field notNullField) {
        Object value = getValue(object, notNullField);

        this.validateNotNull(value, notNullField.getName());
    }

    /**
     * Valide que le champ n'est pas null
     *
     * @param object Objet à valider
     * @param field Champ à valider
     * @return L'instance de validation
     */
    public Validator validateNotNull(Object object, String field) {
        if (Objects.isNull(object)) {
            this.errors.add(new Error("Le champ ne doit pas être vide", field, this.classe));
        }

        return this;
    }

    /**
     * Valide que le champ n'est pas une chaîne de caractères vide ou avec uniquement des espaces (blank)
     *
     * @param object Objet à valider
     * @param notEmptyField Champ à valider
     */
    private void validateNotEmpty(Object object, Field notEmptyField) {
        if (isNotType(notEmptyField, Collection.class) &&
            isNotType(notEmptyField, String.class)) {
            throw new TechnicalException("L'annotation @NotEmpty ne peut pas être utilisé sur un champ de type : " + notEmptyField.getType() + ", " + notEmptyField);
        }

        Object value = getValue(object, notEmptyField);

        if (Objects.isNull(value)) {
            this.validateNotNull(null, notEmptyField.getName());
            return;
        }

        switch (value) {
            case String string -> this.validateNotEmpty(string, notEmptyField.getName());
            case Collection<?> collection -> this.validateNotEmpty(collection, notEmptyField.getName());
            default -> this.validateNotNull(null, notEmptyField.getName());
        }
    }

    /**
     * Valide que le champ n'est pas une chaîne de caractères vide ou avec uniquement des espaces (blank)
     *
     * @param object Objet à valider
     * @param field Champ à valider
     * @return L'instance de validation
     */
    public Validator validateNotEmpty(String object, String field) {
        if (StringUtils.isBlank(object)) {
            this.errors.add(new Error("Le champ ne doit pas être vide", field, this.classe));
        }

        return this;
    }

    /**
     * Valide que le champ n'est pas une chaîne de caractères vide ou avec uniquement des espaces (blank)
     *
     * @param object Objet à valider
     * @param field Champ à valider
     * @return L'instance de validation
     */
    public Validator validateNotEmpty(Collection<?> object, String field) {
        if (CollectionUtils.isEmpty(object)) {
            this.errors.add(new Error("La liste ne doit pas être vide", field, this.classe));
        }

        return this;
    }

    /**
     * Valide que le champ est supérieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param minValueField Champ à valider
     */
    private void validateMinValue(Object object, Field minValueField) {
        if (isNotNumber(minValueField)) {
            throw new TechnicalException("L'annotation @MinValue ne peut pas être utilisé sur un champ de type : " + minValueField.getType() + ", " + minValueField);
        }

        Object value = getValue(object, minValueField);
        double minValue = minValueField.getAnnotation(MinValue.class).value();

        if (value instanceof Number number) {
            this.validateMinValue(number.doubleValue(), minValue, minValueField.getName());
        } else {
            this.validateNotNull(value, minValueField.getName());
        }
    }

    /**
     * Valide que le champ est supérieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param minValue Valeur minimum
     * @param field Champ à valider
     * @return L'instance de validation
     */
    public Validator validateMinValue(Double object, Double minValue, String field) {
        if (object < minValue) {
            this.errors.add(new Error("La valeur du champ doit être supérieur à " + minValue, field, this.classe));
        }

        return this;
    }

    /**
     * Valide que le champ est inférieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param maxValueField Champ à valider
     */
    private void validateMaxValue(Object object, Field maxValueField) {
        if (isNotNumber(maxValueField)) {
            throw new TechnicalException("L'annotation @MaxValue ne peut pas être utilisé sur un champ de type : " + maxValueField.getType() + ", " + maxValueField);
        }

        Object value = getValue(object, maxValueField);
        double maxValue = maxValueField.getAnnotation(MaxValue.class).value();

        if (value instanceof Number number) {
            this.validateMaxValue(number.doubleValue(), maxValue, maxValueField.getName());
        } else {
            this.validateNotNull(value, maxValueField.getName());
        }
    }

    /**
     * Valide que le champ est supérieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param maxValue Valeur minimum
     * @param field Champ à valider
     * @return L'instance de validation
     */
    public Validator validateMaxValue(Double object, Double maxValue, String field) {
        if (object > maxValue) {
            this.errors.add(new Error("La valeur du champ doit être inférieur à " + maxValue, field, this.classe));
        }

        return this;
    }

    /**
     * Valide le champ en cascade
     *
     * @param object Objet à valider
     * @param cascadeValidationField Champ à valider en cascade
     */
    private void validateCascade(Object object, Field cascadeValidationField) {
        Object value = getValue(object, cascadeValidationField);

        // Dans le cas ou l'objet se contient lui-même, on évite un StackOverflowError
        if (object == value) {
            return;
        }

        this.validateWithoutNullCheck(value, cascadeValidationField.getType());
    }

    /**
     * Valide la classe
     */
    public void execute() {
        if (CollectionUtils.isNotEmpty(errors)) {
            throw new ValidationException(errors);
        }
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

    /**
     * Vérifie que l'annotation est sur le bon type
     *
     * @param field Champ
     * @param type Type
     * @return True si le type est valide
     */
    private static boolean isNotType(Field field, Class<?> type) {
        return !type.isAssignableFrom(field.getType());
    }

    /**
     * Renvoi true si le type du champ n'est pas un nombre
     * @param valueField Champ à valider
     * @return True si pas un nombre
     */
    private static boolean isNotNumber(Field valueField) {
        return isNotType(valueField, Number.class) &&
                isNotType(valueField, int.class) &&
                isNotType(valueField, double.class) &&
                isNotType(valueField, float.class) &&
                isNotType(valueField, long.class) &&
                isNotType(valueField, short.class);
    }
}
