package ch.glauser.gestionstock.validation.maxvalue;

import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.common.ValidationUtils;
import ch.glauser.gestionstock.validation.common.Validator;
import ch.glauser.gestionstock.validation.exception.TechnicalException;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@NoArgsConstructor
public class ValidatorMaxValue implements Validator {

    @Override
    public void validate(Validation validation, Object object, Field field) {
        if (ValidationUtils.isNotNumber(field)) {
            throw new TechnicalException("L'annotation @MaxValue ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);
        double maxValue = field.getAnnotation(MaxValue.class).value();

        if (value instanceof Number number) {
            this.validate(validation, number.doubleValue(), maxValue, field.getName());
        }
    }

    /**
     * Valide que le champ est supérieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param maxValue Valeur minimum
     * @param field Champ à valider
     */
    public void validate(Validation validation, Double object, Double maxValue, String field) {
        if (object > maxValue) {
            validation.addError("La valeur du champ doit être inférieur à " + maxValue, field);
        }
    }
}
