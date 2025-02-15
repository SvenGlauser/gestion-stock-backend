package ch.glauser.gestionstock.categorie.repository;

import ch.glauser.gestionstock.categorie.entity.CategorieEntity;
import ch.glauser.gestionstock.common.pagination.Filter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * JPA Repository pour la gestion des categories
 */
@Repository
public interface CategorieJpaRepository extends JpaRepository<CategorieEntity, Long>, JpaSpecificationExecutor<CategorieEntity> {
    Optional<CategorieEntity> findOptionalById(Long id);

    default Page<CategorieEntity> search(Collection<Filter> filters, Pageable pageable) {
        return findAll(
                (Specification<CategorieEntity>) (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    for (Filter filter : filters) {
                        if (Objects.nonNull(filter.getValue())) {
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(filter.getField()), filter.getValue())));
                        }
                    }

                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                },
                pageable
        );
    }
}
