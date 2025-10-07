package ch.glauser.validation.notempty;

import ch.glauser.utilities.exception.TechnicalException;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.common.ValidationUtils;
import ch.glauser.validation.common.Validator;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

@NoArgsConstructor
public class ValidatorNotEmpty implements Validator {

    @Override
    public void validate(Validation validation, Object object, Field field) {
        if (ValidationUtils.isNotType(field, Collection.class) &&
            ValidationUtils.isNotType(field, String.class)) {
            throw new TechnicalException("L'annotation @NotEmpty ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);

        if (Objects.nonNull(value)) {
            switch (value) {
                case String string -> ValidatorNotEmpty.validate(validation, string, field.getName());
                case Collection<?> collection -> ValidatorNotEmpty.validate(validation, collection, field.getName());
                default ->
                        throw new TechnicalException("L'annotation @NotEmpty ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
            }
        }
    }

    /**
     * Valide que le champ n'est pas une chaîne de caractères vide ou avec uniquement des espaces (blank)
     *
     * @param validation Instance de validation
     * @param object Objet à valider
     * @param field Champ à valider
     */
    public static void validate(Validation validation, String object, String field) {
        if (StringUtils.isBlank(object)) {
            validation.addError("Le champ ne doit pas être vide", field);
        }
    }

    /**
     * Valide que le champ n'est pas une liste vide
     *
     * @param validation Instance de validation
     * @param object Objet à valider
     * @param field Champ à valider
     */
    public static void validate(Validation validation, Collection<?> object, String field) {
        if (CollectionUtils.isEmpty(object)) {
            validation.addError("La liste ne doit pas être vide", field);
        }
    }
}
