package ch.glauser.gestionstock.validation.cascade;

import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.common.ValidationUtils;
import ch.glauser.gestionstock.validation.common.Validator;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Objects;

@NoArgsConstructor
public class ValidatorCascadeValidation implements Validator<CascadeValidation> {

    @Override
    public void validate(Validation validation, Object object, Field field) {
        Object value = ValidationUtils.getValue(object, field);

        // Dans le cas ou l'objet se contient lui-même, on évite un StackOverflowError
        // == Car check la référence et non la valeur
        if (object == value) {
            return;
        }

        if (Objects.isNull(value)) {
            return;
        }

        validation.addErrors(
                Validation
                        .validate(value, value.getClass())
                        .getErrors()
        );
    }
}
