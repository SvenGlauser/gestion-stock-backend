package ch.glauser.gestionstock.validation.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Interface représentant un validateur de données
 */
public interface Validator<T extends Annotation> {

    /**
     * Validation de l'objet grâce au champ
     * @param validation Instance de la validation
     * @param object Objet à valider
     * @param field Champ
     */
    void validate(Validation validation, Object object, Field field);
}
