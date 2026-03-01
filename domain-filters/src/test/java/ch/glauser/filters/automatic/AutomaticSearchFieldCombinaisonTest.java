package ch.glauser.filters.automatic;

import ch.glauser.filters.filter.api.SearchFieldCombinaisonType;
import ch.glauser.filters.sort.api.Direction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class AutomaticSearchFieldCombinaisonTest {

    @Test
    void and() {
        final AutomaticSearchField<Object> nameSearchField = new AutomaticSearchField<>();
        final AutomaticSearchField<Object> ageSearchField = new AutomaticSearchField<>();

        final List<AutomaticSearchField<?>> searchFields = List.of(
                nameSearchField,
                ageSearchField
        );

        nameSearchField.setValue("James");
        nameSearchField.setOrder(Direction.ASC);
        nameSearchField.setType(AutomaticSearchField.Type.EQUAL);
        nameSearchField.setField("name");

        ageSearchField.setValue(20);
        ageSearchField.setOrder(Direction.DESC);
        ageSearchField.setType(AutomaticSearchField.Type.EQUAL);
        ageSearchField.setField("age");

        final AutomaticSearchFieldCombinaison combinaison = AutomaticSearchFieldCombinaison.and(searchFields);

        assertThat(combinaison).isNotNull();
        assertThat(combinaison.getFields())
                .isNotNull()
                .containsExactly(nameSearchField, ageSearchField);
        assertThat(combinaison.getType())
                .isNotNull()
                .isEqualTo(SearchFieldCombinaisonType.AND);
    }

    @Test
    void or() {
        final AutomaticSearchField<Object> nameSearchField = new AutomaticSearchField<>();
        final AutomaticSearchField<Object> ageSearchField = new AutomaticSearchField<>();

        final List<AutomaticSearchField<?>> searchFields = List.of(
                nameSearchField,
                ageSearchField
        );

        nameSearchField.setValue("James");
        nameSearchField.setOrder(Direction.ASC);
        nameSearchField.setType(AutomaticSearchField.Type.EQUAL);
        nameSearchField.setField("name");

        ageSearchField.setValue(20);
        ageSearchField.setOrder(Direction.DESC);
        ageSearchField.setType(AutomaticSearchField.Type.EQUAL);
        ageSearchField.setField("age");

        final AutomaticSearchFieldCombinaison combinaison = AutomaticSearchFieldCombinaison.or(searchFields);

        assertThat(combinaison).isNotNull();
        assertThat(combinaison.getFields())
                .isNotNull()
                .containsExactly(nameSearchField, ageSearchField);
        assertThat(combinaison.getType())
                .isNotNull()
                .isEqualTo(SearchFieldCombinaisonType.OR);
    }
}