package ch.glauser.gestionstock.common.validation.notnull;

import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.common.ValidationUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

public class ValidatorNotNull extends Validator {

    /**
     * Construction d'un validateur {@link NotNull}
     * @param validation Validation à utiliser
     */
    public ValidatorNotNull(Validation validation) {
        super(validation);
    }

    @Override
    public void validate(Object object, Field notNullField) {
        Object value = ValidationUtils.getValue(object, notNullField);

        this.validate(value, notNullField.getName());
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return NotNull.class;
    }

    /**
     * Valide que le champ n'est pas null
     *
     * @param object Objet à valider
     * @param field Champ à valider
     */
    public void validate(Object object, String field) {
        if (Objects.isNull(object)) {
            this.validation.addError("Le champ ne doit pas être vide", field);
        }
    }

    /**
     * Valide que le champ est null
     *
     * @param object Objet à valider
     * @param field Champ à valider
     */
    public void validateIsNull(Object object, String field) {
        if (Objects.nonNull(object)) {
            this.validation.addError("Le champ doit pas être vide", field);
        }
    }
}
