package ch.glauser.gestionstock.common.validation.cascade;

import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.common.ValidationUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

public class ValidatorCascadeValidation extends Validator {

    /**
     * Construction d'un validateur {@link CascadeValidation}
     * @param validation Validation à utiliser
     */
    public ValidatorCascadeValidation(Validation validation) {
        super(validation);
    }

    @Override
    public void validate(Object object, Field field) {
        Object value = ValidationUtils.getValue(object, field);

        // Dans le cas ou l'objet se contient lui-même, on évite un StackOverflowError
        // == Car check la référence et non la valeur
        if (object == value) {
            return;
        }

        if (Objects.isNull(value)) {
            return;
        }

        this.validation.addErrors(
                Validation
                        .validate(value, value.getClass())
                        .getErrors()
        );
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return CascadeValidation.class;
    }
}
