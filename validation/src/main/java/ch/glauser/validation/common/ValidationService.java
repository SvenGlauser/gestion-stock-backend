package ch.glauser.validation.common;

import ch.glauser.utilities.exception.TechnicalException;

import java.lang.annotation.Annotation;
import java.util.SequencedSet;

public interface ValidationService {
    <T extends Annotation> SequencedSet<Validator> getValidators(Class<T> annotation);
    <T extends Annotation> void registerValidator(Class<T> annotation, Validator validator) throws TechnicalException;
    <T extends Annotation> void registerValidator(Class<T> annotation, SequencedSet<Validator> validator) throws TechnicalException;

    void validate(Validation validation, Object object) throws TechnicalException;
}
