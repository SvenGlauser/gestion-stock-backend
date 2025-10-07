package ch.glauser.validation.common;

import ch.glauser.utilities.exception.TechnicalException;
import ch.glauser.validation.cascade.CascadeValidation;
import ch.glauser.validation.cascade.ValidatorCascadeValidation;
import ch.glauser.validation.maxvalue.MaxValue;
import ch.glauser.validation.maxvalue.ValidatorMaxValue;
import ch.glauser.validation.minvalue.MinValue;
import ch.glauser.validation.minvalue.ValidatorMinValue;
import ch.glauser.validation.notempty.NotEmpty;
import ch.glauser.validation.notempty.ValidatorNotEmpty;
import ch.glauser.validation.notnull.NotNull;
import ch.glauser.validation.notnull.ValidatorNotNull;
import ch.glauser.validation.regex.Regex;
import ch.glauser.validation.regex.ValidatorRegex;
import ch.glauser.validation.unique.Unique;
import ch.glauser.validation.unique.ValidatorUnique;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class ValidationServiceImpl implements ValidationService {
    private final Map<Class<? extends Annotation>, SequencedSet<Validator>> validatorsMap = new HashMap<>();

    public ValidationServiceImpl() {
        this.registerValidator(NotNull.class, new ValidatorNotNull());

        SequencedSet<Validator> notEmptyValidators = new LinkedHashSet<>(Set.of(
                new ValidatorNotNull(),
                new ValidatorNotEmpty()));
        this.registerValidator(NotEmpty.class, notEmptyValidators);

        this.registerValidator(Unique.class, new ValidatorUnique());

        this.registerValidator(MinValue.class, new ValidatorMinValue());

        this.registerValidator(MaxValue.class, new ValidatorMaxValue());

        this.registerValidator(Regex.class, new ValidatorRegex());

        this.registerValidator(CascadeValidation.class, new ValidatorCascadeValidation());
    }

    @Override
    public <T extends Annotation> void registerValidator(Class<T> annotation, Validator validator) throws TechnicalException {
        if (Objects.isNull(validator)) {
            throw new TechnicalException("Le validateur est null");
        }

        if (Objects.isNull(annotation)) {
            throw new TechnicalException("L'annotation à valider est null");
        }

        final LinkedHashSet<Validator> validators = new LinkedHashSet<>();
        validators.add(validator);
        this.validatorsMap.put(annotation, validators);
    }

    @Override
    public <T extends Annotation> void registerValidator(Class<T> annotation, SequencedSet<Validator> validators) throws TechnicalException {
        this.validatorsMap.put(annotation, new LinkedHashSet<>(CollectionUtils.emptyIfNull(validators)));
    }

    @Override
    public <T extends Annotation> SequencedSet<Validator> getValidators(Class<T> annotation) {
        return new LinkedHashSet<>(CollectionUtils.emptyIfNull(validatorsMap.get(annotation)));
    }

    @Override
    public void validate(Validation validation, Object object) throws TechnicalException {
        for (Field field : findAllFields(object)) {
            final List<Annotation> annotations = List.of(field.getAnnotations());

            for (Annotation annotation : annotations) {
                final SequencedSet<Validator> validators = this.getValidators(annotation.annotationType());

                for (Validator validator : validators) {
                    validator.validate(validation, object, field);
                }
            }
        }
    }

    private List<Field> findAllFields(Object object) {
        final List<Field> fields = new ArrayList<>();

        Class<?> classToValidate = object.getClass();

        while (Objects.nonNull(classToValidate) && !Object.class.equals(classToValidate)) {
            Arrays.stream(classToValidate.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .collect(Collectors.toCollection(() -> fields));

            classToValidate = classToValidate.getSuperclass();
        }

        return fields;
    }
}
