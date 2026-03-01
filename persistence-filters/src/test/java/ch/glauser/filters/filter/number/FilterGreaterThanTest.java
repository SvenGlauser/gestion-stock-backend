package ch.glauser.filters.filter.number;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilterGreaterThanTest {

    @Test
    void of() {
        final SearchField<Integer> searchField = new SearchField<>();
        searchField.setValue(10);

        final FilterGreaterThan<Integer> filterGT = FilterGreaterThan.of(
                searchField,
                "identite", "age");

        assertThat(filterGT).isNotNull();
        assertThat(filterGT.getField())
                .isNotNull()
                .isEqualTo(String.join(".", "identite", "age"));
        assertThat(filterGT.getValue())
                .isNotNull()
                .isEqualTo(10);
    }

    @Test
    void isEmpty() {
        FilterGreaterThan<Integer> filterGT = new FilterGreaterThan<>();
        assertThat(filterGT.isEmpty()).isTrue();

        filterGT.setValue(0);
        assertThat(filterGT.isEmpty()).isFalse();

        filterGT.setValue(1);
        assertThat(filterGT.isEmpty()).isFalse();

        filterGT.setValue(-1);
        assertThat(filterGT.isEmpty()).isFalse();
    }

    @Test
    void validateChild() {
        FilterGreaterThan<Integer> filterGT = new FilterGreaterThan<>();

        Validation validation = filterGT.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).hasSize(1);

        filterGT.setValue(0);

        validation = filterGT.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).isEmpty();
    }

}