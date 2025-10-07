package ch.glauser.validation.regex;

import lombok.Getter;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.function.Predicate;

/**
 * Type de validation possible avec des regex.
 * Tester les Regex sur <a href="https://regex101.com">Regex 101</a>
 */
public enum RegexValidationType {
    EMAIL(RegexValidationType::emailValidation, "Email invalide"),
    PHONE_NUMBER(RegexValidationType::phoneValidation, "Numéro de téléphone invalide");

    RegexValidationType(Predicate<String> validator, String message) {
        this.validator = validator;
        this.message = message;
    }

    @Getter
    private final Predicate<String> validator;

    @Getter
    private final String message;

    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
    private static final String PHONE_NUMBER_REGEX = "((\\+\\d{2}|00\\d{2})\\s?|0)(\\(0\\))?\\d{2}[\\s.-]?\\d{3}[\\s.-]?\\d{2}[\\s.-]?\\d{2}$";

    private static boolean emailValidation(String email) {
        return RegexValidationType.EMAIL_VALIDATOR.isValid(email);
    }

    private static boolean phoneValidation(String phoneNumber) {
        return phoneNumber.matches(RegexValidationType.PHONE_NUMBER_REGEX);
    }
}
