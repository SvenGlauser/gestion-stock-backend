package ch.glauser.gestionstock.common.validation;

/**
 * Exception technique
 */
public final class TechnicalException extends RuntimeException {
    /**
     * Lance une exception
     * @param message Message d'erreur personnalisé
     * @param cause Cause
     */
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
