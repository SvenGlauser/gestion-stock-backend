package ch.glauser.gestionstock.validation.notempty;

import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.common.ValidationUtils;
import ch.glauser.gestionstock.validation.common.Validator;
import ch.glauser.gestionstock.validation.exception.TechnicalException;
import ch.glauser.gestionstock.validation.notnull.ValidatorNotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

public class ValidatorNotEmpty extends Validator {

    private final ValidatorNotNull validatorNotNull;

    /**
     * Construction d'un validateur {@link NotEmpty}
     * @param validation Validation à utiliser
     * @param validatorNotNull Validator
     */
    public ValidatorNotEmpty(Validation validation, ValidatorNotNull validatorNotNull) {
        super(validation);

        this.validatorNotNull = validatorNotNull;
    }

    @Override
    public void validate(Object object, Field field) {
        if (ValidationUtils.isNotType(field, Collection.class) &&
            ValidationUtils.isNotType(field, String.class)) {
            throw new TechnicalException("L'annotation @NotEmpty ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);

        if (Objects.isNull(value)) {
            this.validatorNotNull.validate(null, field.getName());
            return;
        }

        switch (value) {
            case String string -> this.validate(string, field.getName());
            case Collection<?> collection -> this.validate(collection, field.getName());
            default -> throw new TechnicalException("L'annotation @NotEmpty ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return NotEmpty.class;
    }

    /**
     * Valide que le champ n'est pas une chaîne de caractères vide ou avec uniquement des espaces (blank)
     *
     * @param object Objet à valider
     * @param field Champ à valider
     */
    public void validate(String object, String field) {
        if (StringUtils.isBlank(object)) {
            this.validation.addError("Le champ ne doit pas être vide", field);
        }
    }

    /**
     * Valide que le champ n'est pas une liste vide
     *
     * @param object Objet à valider
     * @param field Champ à valider
     */
    public void validate(Collection<?> object, String field) {
        if (CollectionUtils.isEmpty(object)) {
            this.validation.addError("La liste ne doit pas être vide", field);
        }
    }
}
