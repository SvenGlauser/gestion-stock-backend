package ch.glauser.filters.filter.charsequence;

import ch.glauser.filters.field.api.SearchField;
import ch.glauser.validation.common.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilterLikeTest {

    @Test
    void of() {
        final SearchField<String> searchField = new SearchField<>();
        searchField.setValue("Test");

        final FilterLike<String> filterLike = FilterLike.of(
                searchField,
                "categorie", "name");

        assertThat(filterLike).isNotNull();
        assertThat(filterLike.getField())
                .isNotNull()
                .isEqualTo(String.join(".", "categorie", "name"));
        assertThat(filterLike.getValue())
                .isNotNull()
                .isEqualTo("Test");
    }

    @Test
    void isEmpty() {
        FilterLike<String> filterLike = new FilterLike<>();
        assertThat(filterLike.isEmpty()).isTrue();

        filterLike.setValue("");
        assertThat(filterLike.isEmpty()).isTrue();

        filterLike.setValue(" ");
        assertThat(filterLike.isEmpty()).isFalse();

        filterLike.setValue("Test");
        assertThat(filterLike.isEmpty()).isFalse();
    }

    @Test
    void validateChild() {
        FilterLike<String> filterLike = new FilterLike<>();

        Validation validation = filterLike.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).hasSize(1);

        filterLike.setValue(" ");

        validation = filterLike.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).isEmpty();

        filterLike.setValue("Test");

        validation = filterLike.validateChild();
        assertThat(validation).isNotNull();
        assertThat(validation.getErrors()).isEmpty();
    }
}