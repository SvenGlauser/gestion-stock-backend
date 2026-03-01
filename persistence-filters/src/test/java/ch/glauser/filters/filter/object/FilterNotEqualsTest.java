package ch.glauser.filters.filter.object;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilterNotEqualsTest {

    @Test
    void of() {
        final SearchField<Integer> searchField = new SearchField<>();
        searchField.setValue(10);

        final FilterNotEquals<Integer> filterEquals = FilterNotEquals.of(
                searchField,
                "identite", "age");

        assertThat(filterEquals).isNotNull();
        assertThat(filterEquals.getField())
                .isNotNull()
                .isEqualTo(String.join(".", "identite", "age"));
        assertThat(filterEquals.getValue())
                .isNotNull()
                .isEqualTo(10);
    }

    @Test
    void isEmpty() {
        FilterNotEquals<Integer> filterEquals = new FilterNotEquals<>();
        assertThat(filterEquals.isEmpty()).isTrue();

        filterEquals.setValue(0);
        assertThat(filterEquals.isEmpty()).isFalse();
    }

    @Test
    void validateChild() {
        FilterNotEquals<Integer> filterEquals = new FilterNotEquals<>();

        Validation validation = filterEquals.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).hasSize(1);

        filterEquals.setValue(0);

        validation = filterEquals.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).isEmpty();
    }

}