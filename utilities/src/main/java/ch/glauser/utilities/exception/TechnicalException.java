package ch.glauser.utilities.exception;

/**
 * Exception technique
 */
public final class TechnicalException extends RuntimeException {
    /**
     * Lance une exception
     * @param message Message d'erreur personnalisé
     */
    public TechnicalException(String message) {
        super(message);
    }

    /**
     * Lance une exception
     * @param message Message d'erreur personnalisé
     * @param cause Cause
     */
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
