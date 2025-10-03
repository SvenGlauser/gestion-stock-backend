package ch.glauser.gestionstock.validation.unique;

import ch.glauser.gestionstock.validation.common.Validation;
import ch.glauser.gestionstock.validation.common.ValidationUtils;
import ch.glauser.gestionstock.validation.common.Validator;
import ch.glauser.gestionstock.validation.exception.TechnicalException;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class ValidatorUnique extends Validator {

    /**
     * Construction d'un validateur {@link Unique}
     * @param validation Validation à utiliser
     */
    public ValidatorUnique(Validation validation) {
        super(validation);
    }

    @Override
    public void validate(Object object, Field field) {
        if (ValidationUtils.isNotType(field, Collection.class)) {
            throw new TechnicalException("L'annotation @Unique ne peut pas être utilisé sur un champ de type : " + field.getType() + ", " + field);
        }

        Object value = ValidationUtils.getValue(object, field);

        if (value instanceof Collection<?> collection) {
            this.validate(collection, field.getName());
        }
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return Unique.class;
    }

    /**
     * Valide que la liste ne contient pas la même valeur 2 fois
     *
     * @param object Liste à valider
     * @param field Champ à valider
     */
    public void validate(Collection<?> object, String field) {
        if (Objects.isNull(object)) {
            return;
        }

        if (CollectionUtils.size(object) != CollectionUtils.size(Set.copyOf(CollectionUtils.emptyIfNull(object)))) {
            this.validation.addError("La liste doit contenir des valeurs uniques", field);
        }
    }
}
