package ch.glauser.filters.filter.utils;

import ch.glauser.filters.filter.api.CombinaisonType;
import ch.glauser.filters.filter.api.Filter;
import ch.glauser.filters.filter.api.FilterCombinaison;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterUtils {

    /**
     * Créer des filtres pour les recherches
     *
     * @param combinaisons Filtres à appliquer
     * @return Une instance de Specification
     */
    public static <T> Specification<T> specificationOf(Collection<FilterCombinaison> combinaisons) {
        return (root, query, criteriaBuilder) -> toPredicates(combinaisons, root, criteriaBuilder);
    }

    private static <T> Predicate toPredicates(Collection<FilterCombinaison> combinaisons, Path<T> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (FilterCombinaison combinaison : combinaisons) {
            if (Objects.isNull(combinaison.getType())) {
                combinaison.setType(CombinaisonType.AND);
            }

            final Predicate[] combinaisonPredicates = getPredicatesOfCombinaison(combinaison.getFilters(), root, criteriaBuilder);

            switch (combinaison.getType()) {
                case AND -> predicates.add(criteriaBuilder.and(combinaisonPredicates));
                case OR -> predicates.add(criteriaBuilder.or(combinaisonPredicates));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * Transforme une liste de filtre en une liste de Predicate
     *
     * @param filters         Liste de filtres
     * @param root            Racine
     * @param criteriaBuilder CriteriaBuilder
     * @param <T>             Le type du chemin racine
     * @return Un array de Predicate
     */
    public static <T> Predicate[] getPredicatesOfCombinaison(List<Filter<?>> filters, Path<T> root, CriteriaBuilder criteriaBuilder) {
        return filters
                .stream()
                .filter(Objects::nonNull)
                .map(filter -> filter.getPredicate(root, criteriaBuilder))
                .toArray(Predicate[]::new);
    }
}
