package ch.glauser.gestionstock.common.validation;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ValidatorTest {

    @Test
    void validateWithoutClass() {
        testValidation(new Object(), null, 1);
    }

    @Test
    void validateWithoutObject() {
        testValidation(null, Object.class, 1);
    }

    @Test
    void validateNotNull() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.notNull = null;

        testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateNotEmptyString() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.notEmptyString = "";

        testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateNotEmptyCollection() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.notEmptyCollection = List.of();

        testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateNotEmptyNull() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.notEmptyString = null;
        test.notEmptyCollection = null;

        testValidation(test, ValidationClassTest.class, 2);
    }

    @Test
    void validateMinValue() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.minValue = 0;

        testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateMaxValue() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.maxValue = 101;

        testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateCascade() {
        ValidationClassTest testEnfant = new ValidationClassTest();
        testEnfant.init();
        testEnfant.notNull = null;
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.cascadeValidation = testEnfant;

        testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateAll() {
        ValidationClassTest testEnfant = new ValidationClassTest();
        ValidationClassTest test = new ValidationClassTest();
        test.cascadeValidation = testEnfant;

        testValidation(test, ValidationClassTest.class, 10);
    }

    @Test
    void validateCascadeStackOverflow() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.cascadeValidation = test;

        assertDoesNotThrow(() -> Validator.validate(test, ValidationClassTest.class));
    }

    private static <T> void testValidation(T object, Class<T> classe, int errors) {
        assertThatThrownBy(() -> Validator.validate(object, classe))
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

    static class ValidationClassTest {

        @NotNull
        Object notNull;
        @NotEmpty
        String notEmptyString;
        @NotEmpty
        Collection<?> notEmptyCollection;
        @MinValue(0.1)
        int minValue = 0;
        @MaxValue(100.1)
        double maxValue = 101;
        @CascadeValidation
        ValidationClassTest cascadeValidation;

        void init() {
            notNull = new Object();
            notEmptyString = "test";
            notEmptyCollection = List.of("test");
            minValue = 1;
            maxValue = 100;
            cascadeValidation = null;
        }
    }
}