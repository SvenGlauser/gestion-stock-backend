package ch.glauser.gestionstock.validation.notnull;

import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.common.ValidationUtils;
import ch.glauser.gestionstock.validation.common.Validator;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Objects;

@NoArgsConstructor
public class ValidatorNotNull implements Validator {

    @Override
    public void validate(Validation validation, Object object, Field notNullField) {
        Object value = ValidationUtils.getValue(object, notNullField);

        validateNotNull(validation, value, notNullField.getName());
    }

    /**
     * Valide que le champ n'est pas null
     *
     * @param validation Instance de validation
     * @param object Objet à valider
     * @param field Champ à valider
     */
    public static void validateNotNull(Validation validation, Object object, String field) {
        if (Objects.isNull(object)) {
            validation.addError("Le champ ne doit pas être vide", field);
        }
    }

    /**
     * Valide que le champ est null
     *
     * @param validation Instance de validation
     * @param object Objet à valider
     * @param field Champ à valider
     */
    public static void validateIsNull(Validation validation, Object object, String field) {
        if (Objects.nonNull(object)) {
            validation.addError("Le champ doit pas être vide", field);
        }
    }
}
