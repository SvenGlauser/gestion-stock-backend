package ch.glauser.gestionstock.validation.regex;

import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.common.ValidationUtils;
import ch.glauser.gestionstock.validation.common.Validator;
import ch.glauser.gestionstock.validation.exception.TechnicalException;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

@NoArgsConstructor
public class ValidatorRegex implements Validator {

    @Override
    public void validate(Validation validation, Object object, Field field) {
        if (ValidationUtils.isNotType(field, String.class)) {
            throw new TechnicalException("L'annotation @Regex ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);
        RegexValidationType regexValidationType = field.getAnnotation(Regex.class).value();

        if (value instanceof String string) {
            ValidatorRegex.validate(validation, string, regexValidationType, field.getName());
        }
    }

    /**
     * Valide que le champ correspond à une regex
     *
     * @param validation Instance de validation
     * @param object Objet à valider
     * @param regexValidationType Regex
     * @param field Champ à valider
     */
    public static void validate(Validation validation, String object, RegexValidationType regexValidationType, String field) {
        if (StringUtils.isEmpty(object)) {
            return;
        }

        if (Objects.isNull(regexValidationType)) {
            throw new TechnicalException("Le type de regex à valider ne doit pas être null");
        }

        if (!regexValidationType.getValidator().test(object)) {
            validation.addError(regexValidationType.getMessage(),  field);
        }
    }
}
