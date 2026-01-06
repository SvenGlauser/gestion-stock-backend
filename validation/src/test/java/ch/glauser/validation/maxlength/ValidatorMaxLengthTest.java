package ch.glauser.validation.maxlength;

import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorMaxLengthTest {

    static class TestClass {
        @ch.glauser.validation.maxlength.MaxLength(10)
        private String value;
    }

    @Test
    void testValidate() {
        Validation validation = Validation.of(TestClass.class);
        ValidatorMaxLength validatorMaxLength = new ValidatorMaxLength();

        TestClass testClass = new TestClass();

        validateEachFields(validatorMaxLength, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = "0123456789A";

        validateEachFields(validatorMaxLength, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);

        validation.getErrors().clear();
        assertThat(validation.getErrors()).isEmpty();

        testClass.value = "0123456789";

        validateEachFields(validatorMaxLength, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();
    }

    @Test
    void testValidateSpecific() {
        Validation validation = Validation.of(this.getClass());

        ValidatorMaxLength.validate(validation, "0123456789", 0, "testField1");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMaxLength.validate(validation, "0123", 10, "testField2");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMaxLength.validate(validation, null, 10, "testField3");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMaxLength.validate(validation, "0123", 0, "testField4");
        assertThat(validation.getErrors()).hasSize(2);
    }

    private void validateEachFields(ValidatorMaxLength validatorMaxLength, Validation validation, TestClass testClass) {
        for (Field field : TestClass.class.getDeclaredFields()) {
            validatorMaxLength.validate(validation, testClass, field);
        }
    }
}