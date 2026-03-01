package ch.glauser.filters.sort.api;

import ch.glauser.filters.automatic.AutomaticSearchField;
import ch.glauser.filters.field.api.SearchField;
import ch.glauser.validation.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

class SortFieldTest {

    @Test
    void getJpaPaginationOrder() {
        SortField sortField = new SortField();

        assertThatException()
                .isThrownBy(sortField::getJpaPaginationOrder)
                .isInstanceOf(ValidationException.class);

        sortField.setField("Test");

        assertThatException()
                .isThrownBy(sortField::getJpaPaginationOrder)
                .isInstanceOf(ValidationException.class);

        sortField.setField(null);
        sortField.setDirection(Direction.ASC);

        assertThatException()
                .isThrownBy(sortField::getJpaPaginationOrder)
                .isInstanceOf(ValidationException.class);

        sortField.setField("Test");

        Sort.Order jpaPaginationOrder = sortField.getJpaPaginationOrder();
        assertThat(jpaPaginationOrder).isNotNull();
        assertThat(jpaPaginationOrder.getDirection()).isEqualTo(Sort.Direction.ASC);
        assertThat(jpaPaginationOrder.getProperty()).isEqualTo("Test");

        sortField.setDirection(Direction.DESC);

        jpaPaginationOrder = sortField.getJpaPaginationOrder();
        assertThat(jpaPaginationOrder).isNotNull();
        assertThat(jpaPaginationOrder.getDirection()).isEqualTo(Sort.Direction.DESC);
        assertThat(jpaPaginationOrder.getProperty()).isEqualTo("Test");
    }

    @Test
    void ofSearchField() {
        SortField sortField = SortField.of(null, "field");
        assertThat(sortField).isNull();

        sortField = SortField.of(new SearchField<>());
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isNull();
        assertThat(sortField.getField()).isEmpty();


        sortField = SortField.of(new SearchField<>(), "field", "childField");
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isNull();
        assertThat(sortField.getField()).isEqualTo(String.join(".", "field", "childField"));

        final SearchField<Integer> searchField = new SearchField<>();
        searchField.setOrder(Direction.DESC);

        sortField = SortField.of(searchField, "field", "childField");
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isEqualTo(Direction.DESC);
        assertThat(sortField.getField()).isEqualTo(String.join(".", "field", "childField"));
    }

    @Test
    void ofAutomaticSearchField() {
        SortField sortField = SortField.of(null);
        assertThat(sortField).isNull();

        sortField = SortField.of(new AutomaticSearchField<>());
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isNull();
        assertThat(sortField.getField()).isNull();

        final AutomaticSearchField<Integer> searchField = new AutomaticSearchField<>();
        searchField.setOrder(Direction.ASC);
        searchField.setField("field.test");

        sortField = SortField.of(searchField);
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isEqualTo(Direction.ASC);
        assertThat(sortField.getField()).isEqualTo("field.test");
    }

    @Test
    void of() {
        SortField sortField = SortField.of((String) null, null);
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isNull();
        assertThat(sortField.getField()).isNull();

        sortField = SortField.of("field", Direction.ASC);
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isEqualTo(Direction.ASC);
        assertThat(sortField.getField()).isEqualTo("field");
    }

    @Test
    void asc() {
        SortField sortField = SortField.asc(null);
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isEqualTo(Direction.ASC);
        assertThat(sortField.getField()).isNull();

        sortField = SortField.asc("field");
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isEqualTo(Direction.ASC);
        assertThat(sortField.getField()).isEqualTo("field");

    }

    @Test
    void desc() {
        SortField sortField = SortField.desc(null);
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isEqualTo(Direction.DESC);
        assertThat(sortField.getField()).isNull();

        sortField = SortField.desc("field");
        assertThat(sortField).isNotNull();
        assertThat(sortField.getDirection()).isEqualTo(Direction.DESC);
        assertThat(sortField.getField()).isEqualTo("field");
    }
}