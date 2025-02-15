package ch.glauser.gestionstock.common.validation;

import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ValidatorTest {

    @Test
    void validateWithoutClass() {
        TestUtils.testValidation(new Object(), null, 1);
    }

    @Test
    void validateWithoutObject() {
        TestUtils.testValidation(null, Object.class, 1);
    }

    @Test
    void validateNotNull() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.notNull = null;

        TestUtils.testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateNotEmptyString() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.notEmptyString = "";

        TestUtils.testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateNotEmptyCollection() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.notEmptyCollection = List.of();

        TestUtils.testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateNotEmptyNull() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.notEmptyString = null;
        test.notEmptyCollection = null;

        TestUtils.testValidation(test, ValidationClassTest.class, 2);
    }

    @Test
    void validateMinValue() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.minValue = 0;

        TestUtils.testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateMaxValue() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.maxValue = 101;

        TestUtils.testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateCascade() {
        ValidationClassTest testEnfant = new ValidationClassTest();
        testEnfant.init();
        testEnfant.notNull = null;
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.cascadeValidation = testEnfant;

        TestUtils.testValidation(test, ValidationClassTest.class, 1);
    }

    @Test
    void validateAll() {
        ValidationClassTest testEnfant = new ValidationClassTest();
        ValidationClassTest test = new ValidationClassTest();
        test.cascadeValidation = testEnfant;

        TestUtils.testValidation(test, ValidationClassTest.class, 10);
    }

    @Test
    void validateCascadeStackOverflow() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.cascadeValidation = test;

        assertDoesNotThrow(() -> Validator.validate(test, ValidationClassTest.class));
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