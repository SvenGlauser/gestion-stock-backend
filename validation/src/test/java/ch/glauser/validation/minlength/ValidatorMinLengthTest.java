package ch.glauser.validation.minlength;

import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorMinLengthTest {

    static class TestClass {
        @MinLength(10)
        private String value;
    }

    @Test
    void testValidate() {
        Validation validation = Validation.of(TestClass.class);
        ValidatorMinLength validatorMinLength = new ValidatorMinLength();

        TestClass testClass = new TestClass();

        validateEachFields(validatorMinLength, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = "012345678";

        validateEachFields(validatorMinLength, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);

        validation.getErrors().clear();
        assertThat(validation.getErrors()).isEmpty();

        testClass.value = "0123456789";

        validateEachFields(validatorMinLength, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();
    }

    @Test
    void testValidateSpecific() {
        Validation validation = Validation.of(this.getClass());

        ValidatorMinLength.validate(validation, "0123", 5, "testField1");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMinLength.validate(validation, "0123", 0, "testField2");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMinLength.validate(validation, null, 0, "testField3");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMinLength.validate(validation, "0123", 100, "testField4");
        assertThat(validation.getErrors()).hasSize(2);
    }

    private void validateEachFields(ValidatorMinLength validatorMinLength, Validation validation, TestClass testClass) {
        for (Field field : TestClass.class.getDeclaredFields()) {
            validatorMinLength.validate(validation, testClass, field);
        }
    }
}