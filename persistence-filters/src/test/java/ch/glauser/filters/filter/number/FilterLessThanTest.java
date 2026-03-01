package ch.glauser.filters.filter.number;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilterLessThanTest {

    @Test
    void of() {
        final SearchField<Integer> searchField = new SearchField<>();
        searchField.setValue(10);

        final FilterLessThan<Integer> filterLT = FilterLessThan.of(
                searchField,
                "identite", "age");

        assertThat(filterLT).isNotNull();
        assertThat(filterLT.getField())
                .isNotNull()
                .isEqualTo(String.join(".", "identite", "age"));
        assertThat(filterLT.getValue())
                .isNotNull()
                .isEqualTo(10);
    }

    @Test
    void isEmpty() {
        FilterLessThan<Integer> filterLT = new FilterLessThan<>();
        assertThat(filterLT.isEmpty()).isTrue();

        filterLT.setValue(0);
        assertThat(filterLT.isEmpty()).isFalse();

        filterLT.setValue(1);
        assertThat(filterLT.isEmpty()).isFalse();

        filterLT.setValue(-1);
        assertThat(filterLT.isEmpty()).isFalse();
    }

    @Test
    void validateChild() {
        FilterLessThan<Integer> filterLT = new FilterLessThan<>();

        Validation validation = filterLT.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).hasSize(1);

        filterLT.setValue(0);

        validation = filterLT.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).isEmpty();
    }

}