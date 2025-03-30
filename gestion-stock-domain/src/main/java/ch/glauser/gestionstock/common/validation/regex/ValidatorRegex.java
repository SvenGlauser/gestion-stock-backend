package ch.glauser.gestionstock.common.validation.regex;

import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.common.ValidationUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.TechnicalException;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

public class ValidatorRegex extends Validator {

    /**
     * Construction d'un validateur {@link Regex}
     * @param validation Validation à utiliser
     */
    public ValidatorRegex(Validation validation) {
        super(validation);
    }

    @Override
    public void validate(Object object, Field field) {
        if (ValidationUtils.isNotType(field, String.class)) {
            throw new TechnicalException("L'annotation @Regex ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);
        RegexValidationType regexValidationType = field.getAnnotation(Regex.class).value();

        if (value instanceof String string) {
            this.validate(string, regexValidationType, field.getName());
        }
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return Regex.class;
    }

    /**
     * Valide que le champ correspond à une regex
     *
     * @param object Objet à valider
     * @param regexValidationType Regex
     * @param field Champ à valider
     */
    public void validate(String object, RegexValidationType regexValidationType, String field) {
        if (StringUtils.isEmpty(object)) {
            return;
        }

        if (Objects.isNull(regexValidationType)) {
            throw new TechnicalException("Le type de regex à valider ne doit pas être null");
        }

        if (!regexValidationType.getValidator().test(object)) {
            this.validation.addError(regexValidationType.getMessage(),  field);
        }
    }
}
