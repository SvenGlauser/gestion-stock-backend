package ch.glauser.gestionstock.validation.common;

import ch.glauser.gestionstock.validation.exception.ValidationException;
import ch.glauser.gestionstock.validation.notnull.ValidatorNotNull;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe de validation
 */
public final class Validation {

    private static final ValidationService validationService = new ValidationServiceImpl();

    private final Class<?> classe;

    @Getter
    private final List<Error> errors = new ArrayList<>();

    /**
     * Constructeur
     * @param classe Classe en cours de validation
     */
    private Validation(Class<?> classe) {
        if (Objects.isNull(classe)) {
            throw new ValidationException(new Error("La classe à valider ne doit pas être null", "classe", Object.class));
        }

        this.classe = classe;
    }

    /**
     * Récupérer une instance de validateur
     *
     * @param classe Classe à valider
     * @return L'instance de validation
     */
    public static Validation of(Class<?> classe) {
        return new Validation(classe);
    }

    /**
     * Valide un object
     *
     * @param object Object à valider
     * @param classe Classe à valider
     * @return L'instance de validation
     */
    public static <T> Validation validate(T object, Class<? extends T> classe) {
        if (Objects.isNull(object)) {
            throw new ValidationException(new Error("L'objet à valider ne doit pas être null", "object", classe));
        }

        Validation validation = new Validation(classe);

        return validation.validate(object);
    }

    public <T> Validation validate(T object) {
        if (Objects.isNull(object)) {
            throw new ValidationException(new Error("L'objet à valider ne doit pas être null", "object", classe));
        }

        Validation.validationService.validate(this, object);

        return this;
    }

    /**
     * Valide que le champ n'est pas null
     *
     * @param object Objet à valider
     * @param field Champ à valider
     * @return L'instance de validation
     */
    public Validation validateNotNull(Object object, String field) {
        ValidatorNotNull.validateNotNull(this, object, field);

        return this;
    }

    /**
     * Valide que le champ est null
     *
     * @param object Objet à valider
     * @param field Champ à valider
     * @return L'instance de validation
     */
    public Validation validateIsNull(Object object, String field) {
        ValidatorNotNull.validateIsNull(this, object, field);

        return this;
    }

    /**
     * Ajoute un message d'erreur
     *
     * @param message Message
     * @param field Champ en erreur
     * @return L'instance de validation
     */
    public Validation addError(String message, String field) {
        this.errors.add(new Error(message, field, this.classe));
        return this;
    }

    /**
     * Ajoute des messages d'erreur
     *
     * @param errors Errors
     */
    public void addErrors(List<Error> errors) {
        this.errors.addAll(errors);
    }

    /**
     * Valide la classe
     */
    public void execute() {
        if (CollectionUtils.isNotEmpty(errors)) {
            throw new ValidationException(errors);
        }
    }
}
