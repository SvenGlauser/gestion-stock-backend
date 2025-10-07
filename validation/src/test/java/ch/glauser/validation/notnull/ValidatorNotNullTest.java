package ch.glauser.validation.notnull;

import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorNotNullTest {

    static class TestClass {
        @NotNull
        private Object value;
    }

    @Test
    void testValidate() {
        Validation validation = Validation.of(TestClass.class);
        ValidatorNotNull validatorNotNull = new ValidatorNotNull();

        TestClass testClass = new TestClass();

        validateEachFields(validatorNotNull, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);
        validation.getErrors().clear();
        assertThat(validation.getErrors()).isEmpty();

        testClass.value = 5.0;

        validateEachFields(validatorNotNull, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = new Object();

        validateEachFields(validatorNotNull, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = null;

        validateEachFields(validatorNotNull, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);
    }

    @Test
    void testValidateNotNull() {
        Validation validation = Validation.of(this.getClass());

        ValidatorNotNull.validateNotNull(validation, null, "testField1");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorNotNull.validateNotNull(validation, new Object(), "testField2");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorNotNull.validateNotNull(validation, null, "testField3");
        assertThat(validation.getErrors()).hasSize(2);
    }

    @Test
    void testValidateIsNull() {
        Validation validation = Validation.of(this.getClass());

        ValidatorNotNull.validateIsNull(validation, 10, "testField1");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorNotNull.validateIsNull(validation, null, "testField2");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorNotNull.validateIsNull(validation, new Object(), "testField3");
        assertThat(validation.getErrors()).hasSize(2);
    }

    private void validateEachFields(ValidatorNotNull validatorNotNull, Validation validation, TestClass testClass) {
        for (Field field : TestClass.class.getDeclaredFields()) {
            validatorNotNull.validate(validation, testClass, field);
        }
    }
}