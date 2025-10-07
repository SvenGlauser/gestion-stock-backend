package ch.glauser.validation.cascade;

import ch.glauser.validation.common.Validation;
import ch.glauser.validation.notnull.NotNull;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorCascadeTest {

    static class TestClassWrapper {
        @CascadeValidation
        private TestClass value;
    }

    static class TestClass {
        @NotNull
        private Object value;
    }

    @Test
    void testValidate() {
        Validation validation = Validation.of(TestClassWrapper.class);
        ValidatorCascadeValidation validatorCascadeValidation = new ValidatorCascadeValidation();

        validateEachFields(validatorCascadeValidation, validation, null);

        assertThat(validation.getErrors()).isEmpty();

        TestClassWrapper testClassWrapper = new TestClassWrapper();

        validateEachFields(validatorCascadeValidation, validation, testClassWrapper);

        assertThat(validation.getErrors()).isEmpty();

        testClassWrapper.value = new TestClass();

        validateEachFields(validatorCascadeValidation, validation, testClassWrapper);

        assertThat(validation.getErrors()).hasSize(1);
        validation.getErrors().clear();
        assertThat(validation.getErrors()).isEmpty();

        testClassWrapper.value.value = new Object();

        validateEachFields(validatorCascadeValidation, validation, testClassWrapper);

        assertThat(validation.getErrors()).isEmpty();
    }

    @Test
    void testValidateSpecific() {
        Validation validation = Validation.of(this.getClass());

        ValidatorCascadeValidation.validate(validation, new TestClassWrapper());
        assertThat(validation.getErrors()).isEmpty();

        TestClassWrapper wrapper = new TestClassWrapper();
        wrapper.value = new TestClass();
        ValidatorCascadeValidation.validate(validation, wrapper);
        assertThat(validation.getErrors()).hasSize(1);

        wrapper.value.value = new Object();
        ValidatorCascadeValidation.validate(validation, wrapper);
        assertThat(validation.getErrors()).hasSize(1);
    }

    private void validateEachFields(ValidatorCascadeValidation validatorCascadeValidation, Validation validation, TestClassWrapper testClassWrapper) {
        for (Field field : TestClassWrapper.class.getDeclaredFields()) {
            validatorCascadeValidation.validate(validation, testClassWrapper, field);
        }
    }
}