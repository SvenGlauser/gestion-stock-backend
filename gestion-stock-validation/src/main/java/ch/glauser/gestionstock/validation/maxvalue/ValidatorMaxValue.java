package ch.glauser.gestionstock.validation.maxvalue;

import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.common.ValidationUtils;
import ch.glauser.gestionstock.validation.common.Validator;
import ch.glauser.gestionstock.validation.exception.TechnicalException;
import ch.glauser.gestionstock.validation.notnull.ValidatorNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ValidatorMaxValue extends Validator {

    private final ValidatorNotNull validatorNotNull;

    /**
     * Construction d'un validateur {@link MaxValue}
     * @param validation Validation à utiliser
     * @param validatorNotNull Validator
     */
    public ValidatorMaxValue(Validation validation, ValidatorNotNull validatorNotNull) {
        super(validation);

        this.validatorNotNull = validatorNotNull;
    }

    @Override
    public void validate(Object object, Field field) {
        if (ValidationUtils.isNotNumber(field)) {
            throw new TechnicalException("L'annotation @MaxValue ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);
        double maxValue = field.getAnnotation(MaxValue.class).value();

        if (value instanceof Number number) {
            this.validate(number.doubleValue(), maxValue, field.getName());
        } else {
            this.validatorNotNull.validate(value, field.getName());
        }
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return MaxValue.class;
    }

    /**
     * Valide que le champ est supérieur à la valeur dans l'annotation
     *
     * @param object Objet à valider
     * @param maxValue Valeur minimum
     * @param field Champ à valider
     */
    public void validate(Double object, Double maxValue, String field) {
        if (object > maxValue) {
            this.validation.addError("La valeur du champ doit être inférieur à " + maxValue, field);
        }
    }
}
