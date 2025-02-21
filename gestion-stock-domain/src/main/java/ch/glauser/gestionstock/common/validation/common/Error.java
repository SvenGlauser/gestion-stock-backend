package ch.glauser.gestionstock.common.validation.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * Erreur d'exécution de l'application
 */
@Data
@AllArgsConstructor
public final class Error {
    private String message;
    private String champs;
    private Class<?> classe;

    /**
     * Construit une erreur
     *
     * @param message Message d'erreur
     * @param field Champ concerné
     */
    public Error(String message, Field field) {
        this.message = message;
        this.champs = field.getName();
        this.classe = field.getDeclaringClass();
    }
}
