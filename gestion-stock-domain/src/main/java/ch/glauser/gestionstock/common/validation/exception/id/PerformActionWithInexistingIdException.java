package ch.glauser.gestionstock.common.validation.exception.id;

import ch.glauser.gestionstock.common.model.Model;
import lombok.Getter;

/**
 * Exception lancé lors de l'exécution d'une méthode avec un ID et que celui-ci est inexistant
 */
@Getter
public abstract class PerformActionWithInexistingIdException extends RuntimeException {
    protected final Long id;
    protected final Class<? extends Model> clazz;

    /**
     * Instancie une nouvelle exception
     * @param message Message
     * @param id ID inexistant
     * @param clazz Class
     */
    protected PerformActionWithInexistingIdException(String message, Long id, Class<? extends Model> clazz) {
        super(message);
        this.id = id;
        this.clazz = clazz;
    }
}
