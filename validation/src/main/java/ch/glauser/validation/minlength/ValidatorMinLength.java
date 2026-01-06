package ch.glauser.validation.minlength;

import ch.glauser.utilities.exception.TechnicalException;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.common.ValidationUtils;
import ch.glauser.validation.common.Validator;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.CharSequenceUtils;

import java.lang.reflect.Field;

@NoArgsConstructor
public class ValidatorMinLength implements Validator {

    @Override
    public void validate(Validation validation, Object object, Field field) {
        if (ValidationUtils.isNotType(field, CharSequence.class)) {
            throw new TechnicalException("L'annotation @MinLength ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);
        int minLength = field.getAnnotation(MinLength.class).value();

        if (value instanceof CharSequence charSequence) {
            ValidatorMinLength.validate(validation, charSequence, minLength, field.getName());
        }
    }

    /**
     * Valide que la longueur de la chaîne de caractère est supérieure à la valeur donnée
     *
     * @param charSequence Objet à valider
     * @param minLength Valeur minimum
     * @param field Champ à valider
     */
    public static void validate(Validation validation, CharSequence charSequence, int minLength, String field) {
        if (CharSequenceUtils.toCharArray(charSequence).length < minLength) {
            validation.addError("La longueur du texte doit être supérieur à " + minLength, field);
        }
    }
}
