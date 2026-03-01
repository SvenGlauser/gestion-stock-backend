package ch.glauser.filters.filter.number;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilterLessThanOrEqualsTest {

    @Test
    void of() {
        final SearchField<Integer> searchField = new SearchField<>();
        searchField.setValue(10);

        final FilterLessThanOrEquals<Integer> filterLTE = FilterLessThanOrEquals.of(
                searchField,
                "identite", "age");

        assertThat(filterLTE).isNotNull();
        assertThat(filterLTE.getField())
                .isNotNull()
                .isEqualTo(String.join(".", "identite", "age"));
        assertThat(filterLTE.getValue())
                .isNotNull()
                .isEqualTo(10);
    }

    @Test
    void isEmpty() {
        FilterLessThanOrEquals<Integer> filterLTE = new FilterLessThanOrEquals<>();
        assertThat(filterLTE.isEmpty()).isTrue();

        filterLTE.setValue(0);
        assertThat(filterLTE.isEmpty()).isFalse();

        filterLTE.setValue(1);
        assertThat(filterLTE.isEmpty()).isFalse();

        filterLTE.setValue(-1);
        assertThat(filterLTE.isEmpty()).isFalse();
    }

    @Test
    void validateChild() {
        FilterLessThanOrEquals<Integer> filterLTE = new FilterLessThanOrEquals<>();

        Validation validation = filterLTE.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).hasSize(1);

        filterLTE.setValue(0);

        validation = filterLTE.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).isEmpty();
    }

}