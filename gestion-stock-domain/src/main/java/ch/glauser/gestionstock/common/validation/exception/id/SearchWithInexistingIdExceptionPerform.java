package ch.glauser.gestionstock.common.validation.exception.id;

import ch.glauser.gestionstock.common.model.Model;

/**
 * Exception lancée lors de la recherche avec un id inexistant
 */
public final class SearchWithInexistingIdExceptionPerform extends PerformActionWithInexistingIdException {

    /**
     * Instancie une nouvelle exception
     * @param id ID inexistant
     * @param clazz Class
     */
    public SearchWithInexistingIdExceptionPerform(Long id, Class<? extends Model> clazz) {
        super("Impossible de récupérer l'instance de la classe [" + clazz.getSimpleName() + "] avec l'id [" + id + "] car elle n'existe pas", id, clazz);
    }
}
