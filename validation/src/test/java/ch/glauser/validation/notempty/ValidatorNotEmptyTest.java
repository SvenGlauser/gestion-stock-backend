package ch.glauser.validation.notempty;

import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorNotEmptyTest {

    static class TestStringClass {
        @NotEmpty
        private String value;
    }

    static class TestCollectionClass {
        @NotEmpty
        private Collection<Object> value;
    }

    @Test
    void testValidate() {
        Validation validation = Validation.of(TestStringClass.class);
        ValidatorNotEmpty validatorNotEmpty = new ValidatorNotEmpty();

        TestStringClass testClass = new TestStringClass();

        validateEachFields(validatorNotEmpty, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = "test";

        validateEachFields(validatorNotEmpty, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = "";

        validateEachFields(validatorNotEmpty, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);
    }

    @Test
    void testValidateCollection() {
        Validation validation = Validation.of(TestCollectionClass.class);
        ValidatorNotEmpty validatorNotEmpty = new ValidatorNotEmpty();

        TestCollectionClass testClass = new TestCollectionClass();

        validateEachFields(validatorNotEmpty, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = List.of("test");

        validateEachFields(validatorNotEmpty, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = List.of();

        validateEachFields(validatorNotEmpty, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);
    }

    @Test
    void testValidateString() {
        Validation validation = Validation.of(this.getClass());

        ValidatorNotEmpty.validate(validation, "value", "testField1");
        assertThat(validation.getErrors()).isEmpty();

        ValidatorNotEmpty.validate(validation, "", "testField1");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorNotEmpty.validate(validation, (String) null, "testField2");
        assertThat(validation.getErrors()).hasSize(2);

        ValidatorNotEmpty.validate(validation, (Collection<Object>) null, "testField2");
        assertThat(validation.getErrors()).hasSize(3);

        ValidatorNotEmpty.validate(validation, List.of(), "testField2");
        assertThat(validation.getErrors()).hasSize(4);

        ValidatorNotEmpty.validate(validation, List.of("test", "test", "test"), "testField3");
        assertThat(validation.getErrors()).hasSize(4);
    }

    private void validateEachFields(ValidatorNotEmpty validatorNotEmpty, Validation validation, TestStringClass testClass) {
        for (Field field : TestStringClass.class.getDeclaredFields()) {
            validatorNotEmpty.validate(validation, testClass, field);
        }
    }

    private void validateEachFields(ValidatorNotEmpty validatorNotEmpty, Validation validation, TestCollectionClass testClass) {
        for (Field field : TestCollectionClass.class.getDeclaredFields()) {
            validatorNotEmpty.validate(validation, testClass, field);
        }
    }
}