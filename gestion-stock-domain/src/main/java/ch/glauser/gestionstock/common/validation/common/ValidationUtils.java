package ch.glauser.gestionstock.common.validation.common;

import ch.glauser.gestionstock.common.validation.exception.TechnicalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * Méthode utilitaire pour la validation
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtils {
    /**
     * Récupère la valeur stockée dans le champ
     *
     * @param object Objet
     * @param field  Champ à récupérer
     * @return La valeur du champ
     */
    @SuppressWarnings("java:S3011")
    public static Object getValue(Object object, Field field) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new TechnicalException("L'accès au champs est impossible : " + field.getDeclaringClass().getName() + "." + field.getName(), e);
        }
    }

    /**
     * Vérifie que l'annotation est sur le bon type
     *
     * @param field Champ
     * @param type  Type
     * @return True si le type est valide
     */
    public static boolean isNotType(Field field, Class<?> type) {
        return !type.isAssignableFrom(field.getType());
    }

    /**
     * Renvoi true si le type du champ n'est pas un nombre
     *
     * @param valueField Champ à valider
     * @return True si pas un nombre
     */
    public static boolean isNotNumber(Field valueField) {
        return isNotType(valueField, Number.class) &&
                isNotType(valueField, int.class) &&
                isNotType(valueField, double.class) &&
                isNotType(valueField, float.class) &&
                isNotType(valueField, long.class) &&
                isNotType(valueField, short.class);
    }
}
