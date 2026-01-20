package ch.glauser.filters.field.api;

import ch.glauser.filters.sort.api.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Function;

/**
 * Champ de recherche
 * @param <T> Type de données du champ
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchField<T> {
    private T value;
    private Direction order;

    /**
     * Crée un nouveau champ de recherche en changeant son type
     * @param mapper Mapper de changement de type
     * @return Le champ de recherche
     * @param <R> Le nouveau type de données
     */
    public <R> SearchField<R> cast(Function<T, R> mapper) {
        SearchField<R> searchField = new SearchField<>();
        searchField.setValue(mapper.apply(this.value));
        searchField.setOrder(this.order);
        return searchField;
    }

}
