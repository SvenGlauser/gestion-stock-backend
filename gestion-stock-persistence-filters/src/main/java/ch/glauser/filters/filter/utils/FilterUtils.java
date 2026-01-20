package ch.glauser.filters.filter.utils;

import ch.glauser.filters.filter.api.Filter;
import ch.glauser.filters.filter.api.FilterCombinaison;
import ch.glauser.filters.filter.api.SearchFieldCombinaisonType;
import ch.glauser.utilities.exception.TechnicalException;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Utilitaire pour la gestion des filtres
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterUtils {

    /**
     * Créer des filtres pour les recherches
     *
     * @param combinaisons Filtres à appliquer
     * @return Une instance de Specification
     */
    public static <T> Specification<T> specificationOf(Collection<FilterCombinaison> combinaisons) {
        return (root, _, criteriaBuilder) -> FilterUtils.toPredicates(combinaisons, root, criteriaBuilder);
    }

    private static <T> Predicate toPredicates(Collection<FilterCombinaison> combinaisons,
                                              Path<T> root,
                                              CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (FilterCombinaison combinaison : combinaisons) {
            SearchFieldCombinaisonType type = combinaison.getType();
            if (Objects.isNull(type)) {
                type = SearchFieldCombinaisonType.AND;
            }

            final Predicate[] combinaisonPredicates = FilterUtils.getPredicatesOfCombinaison(
                    combinaison.getFilters(),
                    root,
                    criteriaBuilder
            );

            switch (type) {
                case AND -> predicates.add(criteriaBuilder.and(combinaisonPredicates));
                case OR -> predicates.add(criteriaBuilder.or(combinaisonPredicates));
                default -> {
                    log.error("Le type de combinaison de filtre {} est inconnu", type);
                    throw new TechnicalException("Le type de combinaison de filtre " + type + " est inconnu");
                }
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
     * @throws ValidationException Si les paramètres sont nuls
     * @return Un array de Predicate
     */
    public static <T> Predicate[] getPredicatesOfCombinaison(Collection<Filter<?>> filters,
                                                             Path<T> root,
                                                             CriteriaBuilder criteriaBuilder) throws ValidationException {
        Validation
                .of(FilterUtils.class)
                .validateNotNull(root, "root")
                .validateNotNull(criteriaBuilder, "criteriaBuilder")
                .execute();

        return CollectionUtils
                .emptyIfNull(filters)
                .stream()
                .filter(Objects::nonNull)
                .map(filter -> filter.getPredicate(root, criteriaBuilder))
                .toArray(Predicate[]::new);
    }
}
