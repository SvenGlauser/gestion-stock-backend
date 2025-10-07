package ch.glauser.validation.maxvalue;

import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorMaxValueTest {

    static class TestClass {
        @MaxValue(10.0)
        private Double value;
    }

    @Test
    void testValidate() {
        Validation validation = Validation.of(TestClass.class);
        ValidatorMaxValue validatorMaxValue = new ValidatorMaxValue();

        TestClass testClass = new TestClass();

        validateEachFields(validatorMaxValue, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = 15.0;

        validateEachFields(validatorMaxValue, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);

        validation.getErrors().clear();
        assertThat(validation.getErrors()).isEmpty();

        testClass.value = 3.0;

        validateEachFields(validatorMaxValue, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();
    }

    @Test
    void testValidateSpecific() {
        Validation validation = Validation.of(this.getClass());

        ValidatorMaxValue.validate(validation, 10.0, 0.0, "testField1");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMaxValue.validate(validation, 4.0, 10.0, "testField2");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorMaxValue.validate(validation, 4.0, 0.0, "testField3");
        assertThat(validation.getErrors()).hasSize(2);
    }

    private void validateEachFields(ValidatorMaxValue validatorMaxValue, Validation validation, TestClass testClass) {
        for (Field field : TestClass.class.getDeclaredFields()) {
            validatorMaxValue.validate(validation, testClass, field);
        }
    }
}