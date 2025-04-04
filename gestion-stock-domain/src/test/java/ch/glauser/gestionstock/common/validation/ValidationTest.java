package ch.glauser.gestionstock.common.validation;

import ch.glauser.gestionstock.common.validation.cascade.CascadeValidation;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.common.validation.exception.TechnicalException;
import ch.glauser.gestionstock.common.validation.maxvalue.MaxValue;
import ch.glauser.gestionstock.common.validation.minvalue.MinValue;
import ch.glauser.gestionstock.common.validation.notempty.NotEmpty;
import ch.glauser.gestionstock.common.validation.notnull.NotNull;
import ch.glauser.gestionstock.common.validation.unique.Unique;
import ch.glauser.gestionstock.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ValidationTest {

    @Test
    void validateNotNullWithNullValues() {
        Validation validation = Validation.of(Object.class)
                 .validateNotNull(new Object(), "test1")
                 .validateNotNull(null, "test2")
                 .validateNotNull(null, "test3")
                 .validateNotNull(new Object(), "test4")
                 .validateNotNull(null, "test5");

        TestUtils.testValidation(3, validation::execute);
    }

    @Test
    void validateNotNullWithoutNullValues() {
        Validation validation = Validation.of(Object.class)
                .validateNotNull(new Object(), "test1")
                .validateNotNull(new Object(), "test2");

        assertDoesNotThrow(validation::execute);
    }

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
    void validateIsNull() {
        Validation validation = Validation.of(ValidationTest.class);

        validation.validateIsNull(new Object(), "test");

        TestUtils.testValidation(1, validation::execute);
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
    void validateUnique() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.unique = List.of(1, 2, 3, 4, 5, 5, 6, 7, 8, 8, 9, 9, 10);

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

        TestUtils.testValidation(test, ValidationClassTest.class, 12);
    }

    @Test
    void validateCascadeStackOverflow() {
        ValidationClassTest test = new ValidationClassTest();
        test.init();
        test.cascadeValidation = test;

        assertDoesNotThrow(() -> Validation.validate(test, ValidationClassTest.class));
    }

    @Test
    void validateMinValueAnnotationType() {
        ValidationClassNotNumberMinValueTest test = new ValidationClassNotNumberMinValueTest();
        test.value = new Object();

        assertThatThrownBy(() -> Validation.validate(test, ValidationClassNotNumberMinValueTest.class))
                .isInstanceOf(TechnicalException.class);
    }

    @Test
    void validateMaxValueAnnotationType() {
        ValidationClassNotNumberMaxValueTest test = new ValidationClassNotNumberMaxValueTest();
        test.value = new Object();

        assertThatThrownBy(() -> Validation.validate(test, ValidationClassNotNumberMaxValueTest.class))
                .isInstanceOf(TechnicalException.class);
    }

    @Test
    void validateNotEmptyValueAnnotationType() {
        ValidationClassNotStringOrCollectionTest test = new ValidationClassNotStringOrCollectionTest();
        test.value = new Object();

        assertThatThrownBy(() -> Validation.validate(test, ValidationClassNotStringOrCollectionTest.class))
                .isInstanceOf(TechnicalException.class);
    }

    @Test
    void validateUniqueValueAnnotationType() {
        ValidationClassNotCollectionTest test = new ValidationClassNotCollectionTest();
        test.value = new Object();

        assertThatThrownBy(() -> Validation.validate(test, ValidationClassNotCollectionTest.class))
                .isInstanceOf(TechnicalException.class);
    }

    static class ValidationClassNotNumberMinValueTest {
        @MinValue(0)
        Object value;
    }

    static class ValidationClassNotNumberMaxValueTest {
        @MaxValue(0)
        Object value;
    }

    static class ValidationClassNotStringOrCollectionTest {
        @NotEmpty
        Object value;
    }

    static class ValidationClassNotCollectionTest {
        @Unique
        Object value;
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
        @Unique
        Collection<?> unique = List.of(1, 1);
        @CascadeValidation
        ValidationClassTest cascadeValidation;

        void init() {
            notNull = new Object();
            notEmptyString = "test";
            notEmptyCollection = List.of("test");
            minValue = 1;
            maxValue = 100;
            cascadeValidation = null;
            unique = null;
        }
    }
}