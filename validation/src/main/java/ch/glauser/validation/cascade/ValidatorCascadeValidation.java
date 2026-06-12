package ch.glauser.validation.cascade;

import ch.glauser.validation.common.Validation;
import ch.glauser.validation.common.ValidationUtils;
import ch.glauser.validation.common.Validator;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@NoArgsConstructor
public class ValidatorCascadeValidation implements Validator {

    @Override
    public void validate(Validation validation, Object object, Field field) {
        if (object == null) {
            return;
        }

        Object value = ValidationUtils.getValue(object, field);

        final Collection<Object> objectsToValidate;
        if (value instanceof Collection valueAsCollection) {
            objectsToValidate = valueAsCollection;
        } else {
            objectsToValidate = Collections.singleton(value);
        }

        for (Object objectToValidate: objectsToValidate) {
            // Dans le cas ou l'objet se contient lui-même, on évite un StackOverflowError
            // == Car check la référence et non la valeur.
            if (object == objectToValidate) {
                return;
            }

            if (Objects.isNull(objectToValidate)) {
                return;
            }

            ValidatorCascadeValidation.validate(validation, objectToValidate);
        }
    }

    public static void validate(Validation validation, Object object) {
        validation.addErrors(
                Validation
                        .validate(object, object.getClass())
                        .getErrors()
        );
    }
}
