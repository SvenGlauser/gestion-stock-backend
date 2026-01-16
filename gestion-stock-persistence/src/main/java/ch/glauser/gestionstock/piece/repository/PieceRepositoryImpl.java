package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import ch.glauser.gestionstock.piece.entity.PieceHistoriqueEntity;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceConstantes;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueConstantes;
import ch.glauser.gestionstock.piece.pojo.PieceWithHistoriquePojo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du repository de gestion des pieces
 */
@Repository
@RequiredArgsConstructor
public class PieceRepositoryImpl implements PieceRepository {

    private final PieceJpaRepository pieceJpaRepository;
    private final EntityManager entityManager;

    @Override
    public Optional<Piece> get(Long id) {
        return this.pieceJpaRepository.findById(id).map(ModelEntity::toDomain);
    }

    @Override
    public SearchResult<Piece> search(SearchRequest searchRequest) {
        Page<PieceEntity> page = this.pieceJpaRepository.search(PageUtils.getFiltersCombinators(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public SearchResult<PieceWithHistoriquePojo> searchWithHistorique(SearchRequest searchRequest) {

        Pageable pageable = PageUtils.paginate(searchRequest);

        // FIXME SVG Add metamodel
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
        prepareQuery(
                searchRequest,
                pageable,
                query,
                criteriaBuilder,
                (pieceRoot, pieceHistoriqueEntreeRoot, pieceHistoriqueSortieRoot) -> List.of(
                        pieceRoot,
                        criteriaBuilder.sum(pieceHistoriqueEntreeRoot.get(PieceHistoriqueConstantes.FIELD_DIFFERENCE)),
                        criteriaBuilder.sum(pieceHistoriqueSortieRoot.get(PieceHistoriqueConstantes.FIELD_DIFFERENCE))
                ));

        List<Tuple> result = entityManager
                .createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        prepareQuery(
                searchRequest,
                pageable,
                countQuery,
                criteriaBuilder,
                (pieceRoot, _, _) -> List.of(
                        criteriaBuilder.countDistinct(pieceRoot)
                ));

        Long totalElements = entityManager
                .createQuery(countQuery)
                .getSingleResult();

        List<PieceWithHistoriquePojo> pieces = result
                .stream()
                .map(tuple -> new PieceWithHistoriquePojo(
                        (Piece) tuple.get(0),
                        (Long) tuple.get(1),
                        (Long) tuple.get(2)
                ))
                .toList();

        return PageUtils.of(pieces, pageable.getPageNumber(), pageable.getPageSize(), totalElements);
    }

    private static <T> void prepareQuery(SearchRequest searchRequest,
                                         Pageable pageable,
                                         CriteriaQuery<T> query,
                                         CriteriaBuilder criteriaBuilder,
                                         PieceSelectionFunction selectionFunction) {

        Root<PieceHistoriqueEntity> pieceHistoriqueEntreeRoot = query.from(PieceHistoriqueEntity.class);
        Root<PieceHistoriqueEntity> pieceHistoriqueSortieRoot = query.from(PieceHistoriqueEntity.class);
        Root<PieceEntity> pieceRoot = query.from(PieceEntity.class);

        query.multiselect(selectionFunction.getSelections(
                pieceRoot,
                pieceHistoriqueEntreeRoot,
                pieceHistoriqueSortieRoot));

        final List<RepositoryUtils.MultiplePath<?>> mapOfPaths = List.of(
                new RepositoryUtils.MultiplePath<>("entree", pieceHistoriqueEntreeRoot),
                new RepositoryUtils.MultiplePath<>("sortie", pieceHistoriqueSortieRoot)
        );

        query.where(
                criteriaBuilder.equal(
                        pieceRoot,
                        pieceHistoriqueSortieRoot.get(PieceHistoriqueConstantes.FIELD_PIECE)),
                criteriaBuilder.equal(
                        pieceRoot,
                        pieceHistoriqueEntreeRoot.get(PieceHistoriqueConstantes.FIELD_PIECE)),
                RepositoryUtils.toPredicates(
                        PageUtils.getFiltersCombinators(searchRequest),
                        pieceRoot,
                        mapOfPaths,
                        criteriaBuilder
                )
        );

        query.orderBy(pageable
                .getSort()
                .stream()
                .map(jpaOrder -> {
                    final String property = jpaOrder.getProperty();

                    final Pair<String, Path<?>> fieldNameAndPath = RepositoryUtils.getFieldNameAndPath(
                            property,
                            pieceRoot,
                            mapOfPaths
                    );

                    final Path<Object> expression = fieldNameAndPath
                            .getValue()
                            .get(fieldNameAndPath.getKey());

                    if (jpaOrder.isAscending()) {
                        return criteriaBuilder.asc(expression);
                    } else {
                        return criteriaBuilder.desc(expression);
                    }
                })
                .toList());

        query.groupBy(pieceRoot);
    }

    @FunctionalInterface
    private interface PieceSelectionFunction {
        List<Selection<?>> getSelections(Root<PieceEntity> pieceRoot,
                                              Root<PieceHistoriqueEntity> pieceHistoriqueEntreeRoot,
                                              Root<PieceHistoriqueEntity> pieceHistoriqueSortieRoot);
    }

    @Override
    public List<Piece> searchAll(List<FilterCombinator> filters) {
        return this.pieceJpaRepository
                .searchAll(filters)
                .stream()
                .map(ModelEntity::toDomain)
                .toList();
    }

    @Override
    public List<Piece> findAll() {
        return this.pieceJpaRepository.findAll().stream().map(ModelEntity::toDomain).toList();
    }

    @Override
    public SearchResult<Piece> autocomplete(String searchValue) {
        Specification<PieceEntity> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(
                        criteriaBuilder.concat("",
                        criteriaBuilder.concat(root.get(PieceConstantes.FIELD_NUMERO_INVENTAIRE),
                        criteriaBuilder.concat(" / ",
                                               root.get(PieceConstantes.FIELD_NOM))))),
                        "%" + searchValue.toLowerCase() + "%");

        Pageable pageable = PageUtils.getDefaultPage(List.of(
                Sort.Order.asc(PieceConstantes.FIELD_NUMERO_INVENTAIRE),
                Sort.Order.asc(PieceConstantes.FIELD_NOM)));

        Page<PieceEntity> page = this.pieceJpaRepository.findAll(specification, pageable);
        return PageUtils.transform(page);
    }

    @Override
    public Piece create(Piece piece) {
        return this.pieceJpaRepository.save(new PieceEntity(piece)).toDomain();
    }

    @Override
    public Piece modify(Piece piece) {
        return this.pieceJpaRepository.save(new PieceEntity(piece)).toDomain();
    }

    @Override
    public void delete(Long id) {
        this.pieceJpaRepository.deleteById(id);
    }

    @Override
    public boolean existByIdCategorie(Long id) {
        return this.pieceJpaRepository.existsByIdCategorie(id);
    }

    @Override
    public boolean existByIdFournisseur(Long id) {
        return this.pieceJpaRepository.existsByIdFournisseur(id);
    }

    @Override
    public boolean existByNumeroInventaire(String numeroInventaire) {
        return this.pieceJpaRepository.existsByNumeroInventaire(numeroInventaire);
    }
}
