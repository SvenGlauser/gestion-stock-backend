package ch.glauser.gestionstock.common.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
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
     * @param filters Filtres à appliquer
     * @return Une instance de Specification
     */
    public static <T> Specification<T> specificationOf(Collection<Filter> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Filter filter : filters) {
                if (Objects.nonNull(filter.getValue())) {
                    List<String> fields = List.of(filter.getField().split("\\."));
                    Path<Object> jpaPath = null;

                    for (String field : fields) {
                        if (Objects.nonNull(jpaPath)) {
                            jpaPath = jpaPath.get(field);
                        } else {
                            jpaPath = root.get(field);
                        }
                    }

                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(jpaPath, filter.getValue())));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
