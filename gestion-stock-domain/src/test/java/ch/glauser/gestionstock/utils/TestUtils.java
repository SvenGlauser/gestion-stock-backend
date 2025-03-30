package ch.glauser.gestionstock.utils;

import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Utilitaire pour les tests
 */
public class TestUtils {

    /**
     * Test une validation
     *
     * @param object Objet à valider
     * @param classe Classe de l'objet
     * @param errors Nombre d'erreurs attendues
     * @param <T> Type de l'objet à valider
     */
    public static <T> void testValidation(T object, Class<T> classe, int errors) {
        testValidation(errors, () -> Validation.validate(object, classe).execute());
    }

    /**
     * Test une validation
     *
     * @param errors Nombre d'erreurs attendues
     * @param runnable Fonction à tester
     * @param <T> Type de l'objet à valider
     */
    public static <T> void testValidation(int errors, Runnable runnable) {
        assertThatThrownBy(runnable::run)
                .isInstanceOf(ValidationException.class)
                .extracting("errors")
                .isNotNull()
                .matches(list -> {
                    if (list instanceof Collection<?> collection) {
                        return collection.size() == errors;
                    }
                    return false;
                });
    }
}
