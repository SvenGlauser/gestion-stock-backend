package ch.glauser.validation.minvalue;

import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorMinValueTest {

    static class TestClass {
        @MinValue(10.0)
        private Double value;
    }

    @Test
    void testValidate() {
        Validation validation = Validation.of(TestClass.class);
        ValidatorMinValue validatorMinValue = new ValidatorMinValue();

        TestClass testClass = new TestClass();

        validateEachFields(validatorMinValue, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = 5.0;

        validateEachFields(validatorMinValue, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);

        validation.getErrors().clear();
        assertThat(validation.getErrors()).isEmpty();

        testClass.value = 15.0;

        validateEachFields(validatorMinValue, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();
    }

    @Test
    void testValidateSpecific() {
        Validation validation = Validation.of(this.getClass());

        ValidatorMinValue.validate(validation, 10.0, 100.0, "testField1");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMinValue.validate(validation, 4.0, 0.0, "testField2");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMinValue.validate(validation, 4.0, 100.0, "testField3");
        assertThat(validation.getErrors()).hasSize(2);
    }

    private void validateEachFields(ValidatorMinValue validatorMinValue, Validation validation, TestClass testClass) {
        for (Field field : TestClass.class.getDeclaredFields()) {
            validatorMinValue.validate(validation, testClass, field);
        }
    }
}