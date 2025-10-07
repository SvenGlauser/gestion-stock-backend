package ch.glauser.validation.regex;

import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatorRegexTest {

    static class TestClass {
        @Regex(RegexValidationType.EMAIL)
        private String value;
    }

    @Test
    void testValidate() {
        Validation validation = Validation.of(TestClass.class);
        ValidatorRegex validatorRegex = new ValidatorRegex();

        TestClass testClass = new TestClass();

        validateEachFields(validatorRegex, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();

        testClass.value = "test@test.chhhhh";

        validateEachFields(validatorRegex, validation, testClass);

        assertThat(validation.getErrors()).hasSize(1);

        validation.getErrors().clear();
        assertThat(validation.getErrors()).isEmpty();

        testClass.value = "test@test.ch";

        validateEachFields(validatorRegex, validation, testClass);

        assertThat(validation.getErrors()).isEmpty();
    }

    @Test
    void testValidateSpecific() {
        Validation validation = Validation.of(this.getClass());

        ValidatorRegex.validate(validation, "test", RegexValidationType.EMAIL, "testField1");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorRegex.validate(validation, "test@test.ch", RegexValidationType.EMAIL, "testField2");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorRegex.validate(validation, "032 000 00 00", RegexValidationType.PHONE_NUMBER, "testField3");
        assertThat(validation.getErrors()).hasSize(1);

        ValidatorRegex.validate(validation, "ksajdf9", RegexValidationType.PHONE_NUMBER, "testField3");
        assertThat(validation.getErrors()).hasSize(2);
    }

    private void validateEachFields(ValidatorRegex validatorRegex, Validation validation, TestClass testClass) {
        for (Field field : TestClass.class.getDeclaredFields()) {
            validatorRegex.validate(validation, testClass, field);
        }
    }
}