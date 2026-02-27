package ch.glauser.filters.filter.number;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilterGreaterThanOrEqualsTest {

    @Test
    void of() {
        final SearchField<Integer> searchField = new SearchField<>();
        searchField.setValue(10);

        final FilterGreaterThanOrEquals<Integer> filterGTE = FilterGreaterThanOrEquals.of(
                searchField,
                "identite", "age");

        assertThat(filterGTE).isNotNull();
        assertThat(filterGTE.getField())
                .isNotNull()
                .isEqualTo(String.join(".", "identite", "age"));
        assertThat(filterGTE.getValue())
                .isNotNull()
                .isEqualTo(10);
    }

    @Test
    void isEmpty() {
        FilterGreaterThanOrEquals<Integer> filterGTE = new FilterGreaterThanOrEquals<>();
        assertThat(filterGTE.isEmpty()).isTrue();

        filterGTE.setValue(0);
        assertThat(filterGTE.isEmpty()).isFalse();

        filterGTE.setValue(1);
        assertThat(filterGTE.isEmpty()).isFalse();

        filterGTE.setValue(-1);
        assertThat(filterGTE.isEmpty()).isFalse();
    }

    @Test
    void validateChild() {
        FilterGreaterThanOrEquals<Integer> filterGTE = new FilterGreaterThanOrEquals<>();

        Validation validation = filterGTE.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).hasSize(1);

        filterGTE.setValue(0);

        validation = filterGTE.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).isEmpty();
    }

}