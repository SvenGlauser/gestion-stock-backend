package ch.glauser.filters.field.api;

import ch.glauser.filters.sort.api.Direction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchFieldTest {

    @Test
    void cast() {
        SearchField<Integer> integerSearchField = new SearchField<>();

        integerSearchField.setOrder(Direction.ASC);
        integerSearchField.setValue(120);

        assertThat(integerSearchField.getValue()).isEqualTo(120);
        assertThat(integerSearchField.getOrder()).isEqualTo(Direction.ASC);

        SearchField<String> stringSearchField = integerSearchField.cast(String::valueOf);

        assertThat(stringSearchField.getValue()).isEqualTo("120");
        assertThat(stringSearchField.getOrder()).isEqualTo(Direction.ASC);

        SearchField<Integer> reconvertedSearchField = stringSearchField.cast(Integer::valueOf);

        assertThat(reconvertedSearchField.getValue()).isEqualTo(120);
        assertThat(reconvertedSearchField.getOrder()).isEqualTo(Direction.ASC);
    }
}