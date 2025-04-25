package ch.glauser.gestionstock.common.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Utilitaire utilisé pour la génération de filtres dans les JpaRepository
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RepositoryUtils {
    /**
     * Créer des filtres automatiques pour les recherches
     *
     * @param combinators Filtres à appliquer
     * @return Une instance de Specification
     */
    public static <T> Specification<T> specificationOf(Collection<FilterCombinator> combinators) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (FilterCombinator combinator : combinators) {
                if (Objects.isNull(combinator.getType())) {
                    combinator.setType(FilterCombinator.Type.AND);
                }

                if (Objects.requireNonNull(combinator.getType()) == FilterCombinator.Type.OR) {
                    predicates.add(criteriaBuilder.or(RepositoryUtils.getPredicates(root, criteriaBuilder, combinator)));
                } else if (combinator.getType() == FilterCombinator.Type.AND) {
                    predicates.add(criteriaBuilder.and(RepositoryUtils.getPredicates(root, criteriaBuilder, combinator)));
                }

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static <T> Predicate[] getPredicates(Root<T> root, CriteriaBuilder criteriaBuilder, FilterCombinator combinator) {
        List<Predicate> predicatesOfCombinator = new ArrayList<>();

        for (Filter filter : combinator.getFilters()) {
            if (Objects.nonNull(filter.getValue())) {
                Path<?> jpaPath = getPath(root, filter);

                if (Objects.isNull(filter.getType())) {
                    filter.setType(Filter.Type.EQUAL);
                }

                if (Objects.requireNonNull(filter.getType()) == Filter.Type.EQUAL) {
                    predicatesOfCombinator.add(RepositoryUtils.equalCondition(criteriaBuilder, filter, jpaPath));
                } else if (filter.getType() == Filter.Type.STRING_LIKE) {
                    predicatesOfCombinator.add(RepositoryUtils.likeCondition(criteriaBuilder, filter, (Path<String>) jpaPath));
                }
            }
        }

        return predicatesOfCombinator.toArray(new Predicate[0]);
    }

    private static Predicate equalCondition(CriteriaBuilder criteriaBuilder, Filter filter, Path<?> jpaPath) {
        return criteriaBuilder.equal(jpaPath, filter.getValue());
    }

    private static Predicate likeCondition(CriteriaBuilder criteriaBuilder, Filter filter, Path<String> jpaPath) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(jpaPath),
                "%" + filter.getValue().toString().toLowerCase() + "%");
    }

    /**
     * Récupère le Path JPA
     * @param root Root
     * @param filter Filtre
     * @return Le chemin JPA
     * @param <T> Le type de l'entité recherchée
     */
    private static <T> Path<?> getPath(Root<T> root, Filter filter) {
        List<String> fields = List.of(filter.getField().split("\\."));
        Path<?> jpaPath = null;

        for (String field : fields) {
            if (Objects.nonNull(jpaPath)) {
                jpaPath = jpaPath.get(field);
            } else {
                jpaPath = root.get(field);
            }
        }
        return jpaPath;
    }
}
