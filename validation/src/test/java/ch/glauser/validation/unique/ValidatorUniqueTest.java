package ch.glauser.validation.unique;

import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorUniqueTest {

    static class TestClass {
        @Unique
        private List<Object> value;
    }

    @Test
    void testValidate() {
        Validation validation = Validation.of(TestClass.class);
        ValidatorUnique validatorUnique = new ValidatorUnique();

        TestClass testClass = new TestClass();

        validateEachFields(validatorUnique, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = List.of();

        validateEachFields(validatorUnique, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = List.of(1, 1);

        validateEachFields(validatorUnique, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);

        validation.getErrors().clear();
        assertThat(validation.getErrors()).isEmpty();

        testClass.value = List.of(1, 2);

        validateEachFields(validatorUnique, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = List.of(List.of("test"), List.of("test"));

        validateEachFields(validatorUnique, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);

        validation.getErrors().clear();
        assertThat(validation.getErrors()).isEmpty();

        testClass.value = List.of(List.of("test"), List.of("test1"));

        validateEachFields(validatorUnique, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();
    }

    @Test
    void testValidateSpecific() {
        Validation validation = Validation.of(this.getClass());

        ValidatorUnique.validate(validation, List.of("test", "test"), "testField1");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorUnique.validate(validation, List.of("test", "test1"), "testField2");
        assertThat(validation.getErrors()).hasSize(1);
    }

    private void validateEachFields(ValidatorUnique validatorUnique, Validation validation, TestClass testClass) {
        for (Field field : TestClass.class.getDeclaredFields()) {
            validatorUnique.validate(validation, testClass, field);
        }
    }
}