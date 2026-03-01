package ch.glauser.filters.filter.api;

import ch.glauser.filters.filter.object.FilterEquals;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FilterCombinaisonTest {

    @Test
    void and() {
        final FilterEquals<Integer> filterEquals = new FilterEquals<>();
        filterEquals.setField("field1");
        filterEquals.setValue(1);

        final FilterEquals<Integer> filterEquals2 = new FilterEquals<>();
        filterEquals.setField("field1");
        filterEquals2.setValue(2);

        final Collection<Filter<?>> filterEqualsList = List.of(filterEquals, filterEquals2);

        final FilterCombinaison filterCombinaison = FilterCombinaison.and(filterEqualsList);

        assertThat(filterCombinaison).isNotNull();
        assertThat(filterCombinaison.getType())
                .isNotNull()
                .isEqualTo(SearchFieldCombinaisonType.AND);
        assertThat(filterCombinaison.getFilters())
                .isNotNull()
                .containsExactly(filterEquals, filterEquals2);
    }

    @Test
    void or() {
        final FilterEquals<Integer> filterEquals = new FilterEquals<>();
        filterEquals.setField("field1");
        filterEquals.setValue(1);

        final FilterEquals<Integer> filterEquals2 = new FilterEquals<>();
        filterEquals.setField("field1");
        filterEquals2.setValue(2);

        final Collection<Filter<?>> filterEqualsList = List.of(filterEquals, filterEquals2);

        final FilterCombinaison filterCombinaison = FilterCombinaison.or(filterEqualsList);

        assertThat(filterCombinaison).isNotNull();
        assertThat(filterCombinaison.getType())
                .isNotNull()
                .isEqualTo(SearchFieldCombinaisonType.OR);
        assertThat(filterCombinaison.getFilters())
                .isNotNull()
                .containsExactly(filterEquals, filterEquals2);
    }
}