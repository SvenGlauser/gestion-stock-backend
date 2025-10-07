package ch.glauser.gestionstock.common.exception.id;

import ch.glauser.gestionstock.common.model.Model;

/**
 * Interface fonctionnelle pour passer une méthode d'instanciation d'erreur d'id incorrect en paramètre de méthode
 */
@FunctionalInterface
public interface PerformActionWithInexistingIdFunction {
    PerformActionWithInexistingIdException instantiate(Long id, Class<? extends Model> clazz);
}
