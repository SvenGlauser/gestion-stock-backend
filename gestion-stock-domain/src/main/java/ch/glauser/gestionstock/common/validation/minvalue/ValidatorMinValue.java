package ch.glauser.gestionstock.common.validation.minvalue;

import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.common.ValidationUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.TechnicalException;
import ch.glauser.gestionstock.common.validation.notnull.ValidatorNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ValidatorMinValue extends Validator {

    private final ValidatorNotNull validatorNotNull;

    /**
     * Construction d'un validateur {@link MinValue}
     * @param validation Validation à utiliser
     * @param validatorNotNull Validator
     */
    public ValidatorMinValue(Validation validation, ValidatorNotNull validatorNotNull) {
        super(validation);

        this.validatorNotNull = validatorNotNull;
    }

    @Override
    public void validate(Object object, Field field) {
        if (ValidationUtils.isNotNumber(field)) {
            throw new TechnicalException("L'annotation @MinValue ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);
        double minValue = field.getAnnotation(MinValue.class).value();

        if (value instanceof Number number) {
            this.validate(number.doubleValue(), minValue, field.getName());
        } else {
            this.validatorNotNull.validate(value, field.getName());
        }
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return MinValue.class;
    }

    /**
     * Valide que le champ est inférieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param minValue Valeur minimum
     * @param field Champ à valider
     */
    public void validate(Double object, Double minValue, String field) {
        if (object < minValue) {
            this.validation.addError("La valeur du champ doit être inférieur à " + minValue, field);
        }
    }
}
