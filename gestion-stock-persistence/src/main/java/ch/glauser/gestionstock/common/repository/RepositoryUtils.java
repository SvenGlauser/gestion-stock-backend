package ch.glauser.gestionstock.common.repository;

import ch.glauser.filters.automatic.AutomaticField;
import ch.glauser.filters.automatic.AutomaticFieldCombinator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
    public static <T> Specification<T> specificationOf(Collection<AutomaticFieldCombinator> combinators) {
        return (root, query, criteriaBuilder) -> toPredicates(combinators, root, null, criteriaBuilder);
    }

    public static <T> Predicate toPredicates(Collection<AutomaticFieldCombinator> combinators, Path<T> principalPath, List<MultiplePath<?>> multiplePaths, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (AutomaticFieldCombinator combinator : combinators) {
            if (Objects.isNull(combinator.getType())) {
                combinator.setType(AutomaticFieldCombinator.Type.AND);
            }

            if (Objects.requireNonNull(combinator.getType()) == AutomaticFieldCombinator.Type.OR) {
                predicates.add(criteriaBuilder.or(RepositoryUtils.getPredicates(combinator.getFilters(), principalPath, multiplePaths, criteriaBuilder)));
            } else if (combinator.getType() == AutomaticFieldCombinator.Type.AND) {
                predicates.add(criteriaBuilder.and(RepositoryUtils.getPredicates(combinator.getFilters(), principalPath, multiplePaths, criteriaBuilder)));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private static <T> Predicate[] getPredicates(List<AutomaticField<?>> filtres, Path<T> principalPath, List<MultiplePath<?>> multiplePaths, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesOfCombinator = new ArrayList<>();

        for (AutomaticField<?> filter : filtres) {
            if (Objects.nonNull(filter.getValue())) {
                Path<?> jpaPath = getPath(principalPath, multiplePaths, filter);

                if (Objects.isNull(filter.getType())) {
                    filter.setType(AutomaticField.Type.EQUAL);
                }

                if (Objects.requireNonNull(filter.getType()) == AutomaticField.Type.EQUAL) {
                    predicatesOfCombinator.add(RepositoryUtils.equalCondition(criteriaBuilder, filter, jpaPath));
                } else if (filter.getType() == AutomaticField.Type.STRING_LIKE) {
                    predicatesOfCombinator.add(RepositoryUtils.likeCondition(criteriaBuilder, filter, (Path<String>) jpaPath));
                }
            }
        }

        return predicatesOfCombinator.toArray(new Predicate[0]);
    }

    private static Predicate equalCondition(CriteriaBuilder criteriaBuilder, AutomaticField filter, Path<?> jpaPath) {
        return criteriaBuilder.equal(jpaPath, filter.getValue());
    }

    private static Predicate likeCondition(CriteriaBuilder criteriaBuilder, AutomaticField filter, Path<String> jpaPath) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(jpaPath),
                "%" + filter.getValue().toString().toLowerCase() + "%");
    }

    public static Pair<String, Path<?>> getFieldNameAndPath(String field, Path<?> principalPath, List<MultiplePath<?>> roots) {
        return CollectionUtils
                .emptyIfNull(roots)
                .stream()
                .filter(additionalRoot -> Objects.nonNull(additionalRoot.root()))
                .filter(additionalRoot -> StringUtils.isNoneBlank(additionalRoot.fieldPrefix()))
                .filter(additionalRoot -> StringUtils.startsWithIgnoreCase(
                        field,
                        additionalRoot.fieldPrefix()
                ))
                .findFirst()
                .map(additionalRoot -> {
                    final String modifiedField = StringUtils.removeStartIgnoreCase(
                            field,
                            additionalRoot.fieldPrefix());

                    final String cleanedField = StringUtils.removeStartIgnoreCase(
                            modifiedField,
                            ".");

                    return ImmutablePair.<String, Path<?>>of(
                            cleanedField,
                            additionalRoot.root()
                    );
                })
                .orElse(ImmutablePair.of(field, principalPath));
    }

    /**
     * Récupère le Path JPA
     * @param principalPath Root
     * @param multiplePaths Chemins supplémentaires
     * @param filter Filtre
     * @return Le chemin JPA
     * @param <T> Le type de l'entité recherchée
     */
    private static <T> Path<?> getPath(Path<T> principalPath, List<MultiplePath<?>> multiplePaths, AutomaticField filter) {
        List<String> fields = new ArrayList<>(List.of(filter.getField().split("\\.")));

        if (fields.isEmpty()) {
            return null;
        }

        Pair<String, Path<?>> fieldNameAndRoot = getFieldNameAndPath(fields.getFirst(), principalPath, multiplePaths);
        fields.set(0, fieldNameAndRoot.getKey());

        Path<?> jpaPath = null;

        for (String field : fields.stream().filter(StringUtils::isNoneBlank).toList()) {
            if (Objects.nonNull(jpaPath)) {
                jpaPath = jpaPath.get(field);
            } else {
                jpaPath = fieldNameAndRoot.getValue().get(field);
            }
        }
        return jpaPath;
    }

    public record MultiplePath<T>(String fieldPrefix, Path<T> root) {
    }
}
