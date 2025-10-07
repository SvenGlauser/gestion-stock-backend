package ch.glauser.validation.minvalue;

import ch.glauser.utilities.exception.TechnicalException;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.common.ValidationUtils;
import ch.glauser.validation.common.Validator;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor
public class ValidatorMinValue implements Validator {

    @Override
    public void validate(Validation validation, Object object, Field field) {
        if (ValidationUtils.isNotNumber(field)) {
            throw new TechnicalException("L'annotation @MinValue ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);
        double minValue = field.getAnnotation(MinValue.class).value();

        if (value instanceof Number number) {
            ValidatorMinValue.validate(validation, number.doubleValue(), minValue, field.getName());
        }
    }

    /**
     * Valide que le champ est inférieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param minValue Valeur minimum
     * @param field Champ à valider
     */
    public static void validate(Validation validation, Double object, Double minValue, String field) {
        if (object < minValue) {
            validation.addError("La valeur du champ doit être supérieur à " + minValue, field);
        }
    }
}
