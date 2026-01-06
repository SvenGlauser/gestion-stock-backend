package ch.glauser.validation.maxlength;

import ch.glauser.utilities.exception.TechnicalException;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.common.ValidationUtils;
import ch.glauser.validation.common.Validator;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.CharSequenceUtils;

import java.lang.reflect.Field;

@NoArgsConstructor
public class ValidatorMaxLength implements Validator {

    @Override
    public void validate(Validation validation, Object object, Field field) {
        if (ValidationUtils.isNotType(field, CharSequence.class)) {
            throw new TechnicalException("L'annotation @MaxLength ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);
        int maxLength = field.getAnnotation(MaxLength.class).value();

        if (value instanceof CharSequence charSequence) {
            ValidatorMaxLength.validate(validation, charSequence, maxLength, field.getName());
        }
    }

    /**
     * Valide que la longueur de la chaîne de caractère est inférieur à la valeur donnée
     *
     * @param charSequence Objet à valider
     * @param maxLength Valeur maximum
     * @param field Champ à valider
     */
    public static void validate(Validation validation, CharSequence charSequence, int maxLength, String field) {
        if (CharSequenceUtils.toCharArray(charSequence).length > maxLength) {
            validation.addError("La longueur du texte doit être inférieur à " + maxLength, field);
        }
    }
}
