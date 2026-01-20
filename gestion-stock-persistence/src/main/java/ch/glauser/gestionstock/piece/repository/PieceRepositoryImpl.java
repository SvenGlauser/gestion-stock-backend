package ch.glauser.gestionstock.piece.repository;

import ch.glauser.filters.automatic.AutomaticFieldCombinator;
import ch.glauser.filters.field.api.Field;
import ch.glauser.filters.filter.charsequence.FilterLike;
import ch.glauser.filters.filter.number.FilterGreaterThanOrEquals;
import ch.glauser.filters.filter.number.FilterLessThanOrEquals;
import ch.glauser.filters.filter.object.FilterEquals;
import ch.glauser.filters.filter.utils.FilterUtils;
import ch.glauser.filters.searchquery.utils.SearchQueryMapper;
import ch.glauser.filters.searchquery.utils.SearchQueryMapperBuilder;
import ch.glauser.filters.searchquery.utils.SearchQueryUtils;
import ch.glauser.filters.utils.PaginationUtils;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.entity.ModelEntity_;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import ch.glauser.gestionstock.piece.entity.PieceEntity_;
import ch.glauser.gestionstock.piece.entity.PieceHistoriqueEntity;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceConstantes;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueConstantes;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueType;
import ch.glauser.gestionstock.piece.pojo.PieceWithHistoriquePojo;
import ch.glauser.gestionstock.piece.search.PieceSearchQuery;
import ch.glauser.gestionstock.piece.search.PieceWithHistoriqueSearchQuery;
import ch.glauser.gestionstock.piece.view.PieceHistoriqueView_;
import ch.glauser.gestionstock.piece.view.PieceWithHistoriqueView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * Implémentation du repository de gestion des pieces
 */
@Repository
@RequiredArgsConstructor
public class PieceRepositoryImpl implements PieceRepository {

    private static final List<SearchQueryMapper<PieceSearchQuery, ?>> PIECE_SQ_MAPPER = SearchQueryMapperBuilder
            .<PieceSearchQuery>of()
            .add(
                    PieceSearchQuery::getNumeroInventaire,
                    FilterLike::new,
                    PieceEntity_.NUMERO_INVENTAIRE)
            .add(
                    PieceSearchQuery::getNom,
                    FilterLike::new,
                    PieceEntity_.NOM)
            .add(
                    PieceSearchQuery::getCategorieId,
                    FilterEquals::new,
                    PieceEntity_.CATEGORIE, ModelEntity_.ID)
            .add(
                    PieceSearchQuery::getPrix,
                    FilterEquals::new,
                    PieceEntity_.PRIX)
            .add(
                    PieceSearchQuery::getQuantite,
                    FilterEquals::new,
                    PieceEntity_.QUANTITE)
            .build();

    private final PieceJpaRepository pieceJpaRepository;
    private final EntityManager entityManager;

    @Override
    public Optional<Piece> get(Long id) {
        return this.pieceJpaRepository.findById(id).map(ModelEntity::toDomain);
    }

    @Override
    public SearchResult<Piece> search(PieceSearchQuery searchQuery) {
        Page<PieceEntity> page = this.pieceJpaRepository.search(
                SearchQueryUtils.filterCombinaison(searchQuery, PIECE_SQ_MAPPER),
                SearchQueryUtils.paginate(searchQuery, PIECE_SQ_MAPPER));

        return PageUtils.transform(page);
    }

    @Override
    public SearchResult<PieceWithHistoriquePojo> searchWithHistorique(PieceWithHistoriqueSearchQuery searchQuery) {

        final List<SearchQueryMapper<PieceWithHistoriqueSearchQuery, ?>> pieceSQMapper = SearchQueryMapperBuilder
                .<PieceWithHistoriqueSearchQuery>of()
                .add(
                        PieceWithHistoriqueSearchQuery::getNumeroInventaire,
                        FilterLike::new,
                        PieceEntity_.NUMERO_INVENTAIRE)
                .add(
                        PieceWithHistoriqueSearchQuery::getNom,
                        FilterLike::new,
                        PieceEntity_.NOM)
                .add(
                        PieceWithHistoriqueSearchQuery::getCategorieId,
                        FilterEquals::new,
                        PieceEntity_.CATEGORIE, ModelEntity_.ID)
                .add(
                        PieceWithHistoriqueSearchQuery::getPrix,
                        FilterEquals::new,
                        PieceEntity_.PRIX)
                .add(
                        PieceWithHistoriqueSearchQuery::getQuantite,
                        FilterEquals::new,
                        PieceEntity_.QUANTITE)
                .build();

        final List<SearchQueryMapper<PieceWithHistoriqueSearchQuery, ?>> historiqueSQMapper = SearchQueryMapperBuilder
                .<PieceWithHistoriqueSearchQuery>of()
                .add(
                        innerSearchQuery -> Optional
                                .ofNullable(innerSearchQuery.getDateDebut())
                                .<Field<ChronoLocalDateTime<?>>>map(dateDebut -> dateDebut.cast(date -> LocalDateTime.of(date, LocalDateTime.MIN.toLocalTime())))
                                .orElse(null),
                        FilterGreaterThanOrEquals::new,
                        PieceHistoriqueView_.HEURE)
                .add(
                        innerSearchQuery -> Optional
                                .ofNullable(innerSearchQuery.getDateFin())
                                .<Field<ChronoLocalDateTime<?>>>map(dateFin -> dateFin.cast(date -> LocalDateTime.of(date, LocalDateTime.MAX.toLocalTime())))
                                .orElse(null),
                        FilterLessThanOrEquals::new,
                        PieceHistoriqueView_.HEURE)
                .build();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        List<PieceWithHistoriquePojo> result = searchQuery(searchQuery, criteriaBuilder, pieceSQMapper, historiqueSQMapper);
        Long totalElements = countQuery(searchQuery, criteriaBuilder, pieceSQMapper, historiqueSQMapper);

        return PageUtils.of(result, searchQuery.getPage(), searchQuery.getPageSize(), totalElements);
    }

    private Long countQuery(PieceWithHistoriqueSearchQuery searchQuery,
                            CriteriaBuilder criteriaBuilder,
                            List<SearchQueryMapper<PieceWithHistoriqueSearchQuery, ?>> pieceSQMapper,
                            List<SearchQueryMapper<PieceWithHistoriqueSearchQuery, ?>> historiqueSQMapper) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<PieceWithHistoriqueView> pieceMainRoot = countQuery.from(PieceWithHistoriqueView.class);

        Subquery<PieceWithHistoriqueView> subQuery = countQuery.subquery(PieceWithHistoriqueView.class);

        JoinedContext joinedContext = applyJoinsAndAggregations(
                subQuery,
                criteriaBuilder,
                searchQuery,
                historiqueSQMapper);

        subQuery.select(joinedContext.root);

        subQuery.where(
            criteriaBuilder.and(
                FilterUtils.getPredicatesOfCombinaison(
                        SearchQueryUtils.getFilters(searchQuery, pieceSQMapper),
                        joinedContext.root,
                        criteriaBuilder
                )
            ),
            criteriaBuilder.equal(joinedContext.root, pieceMainRoot)
        );

        countQuery.select(criteriaBuilder.count(pieceMainRoot));
        countQuery.where(criteriaBuilder.exists(subQuery));

        return entityManager
                .createQuery(countQuery)
                .getSingleResult();
    }

    private List<PieceWithHistoriquePojo> searchQuery(PieceWithHistoriqueSearchQuery searchQuery,
                                                      CriteriaBuilder criteriaBuilder,
                                                      List<SearchQueryMapper<PieceWithHistoriqueSearchQuery, ?>> pieceSQMapper,
                                                      List<SearchQueryMapper<PieceWithHistoriqueSearchQuery, ?>> historiqueSQMapper) {
        CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();

        JoinedContext joinedContext = applyJoinsAndAggregations(
                query,
                criteriaBuilder,
                searchQuery,
                historiqueSQMapper);

        query.multiselect(
                joinedContext.root,
                joinedContext.sumEntrees,
                joinedContext.sumSorties);

        query.where(
                criteriaBuilder.and(
                        FilterUtils.getPredicatesOfCombinaison(
                                SearchQueryUtils.getFilters(searchQuery, pieceSQMapper),
                                joinedContext.root,
                                criteriaBuilder
                        )
                )
        );

        List<Order> havingOrders = new ArrayList<>();

        if (Objects.nonNull(searchQuery.getQuantiteEntree())
            && Objects.nonNull(searchQuery.getQuantiteEntree().getOrder())) {

            switch (searchQuery.getQuantiteEntree().getOrder()) {
                case ASC -> havingOrders.add(criteriaBuilder.asc(joinedContext.sumEntrees));
                case DESC -> havingOrders.add(criteriaBuilder.desc(joinedContext.sumEntrees));
            }
        }

        if (Objects.nonNull(searchQuery.getQuantiteSortie())
            && Objects.nonNull(searchQuery.getQuantiteSortie().getOrder())) {

            switch (searchQuery.getQuantiteSortie().getOrder()) {
                case ASC -> havingOrders.add(criteriaBuilder.asc(joinedContext.sumSorties));
                case DESC -> havingOrders.add(criteriaBuilder.desc(joinedContext.sumSorties));
            }
        }

        query.orderBy(Stream
                .of(
                        SearchQueryUtils
                                .getSorts(searchQuery, pieceSQMapper)
                                .stream()
                                .map(sort -> sort.getOrder(joinedContext.root, criteriaBuilder))
                                .toList(),
                        SearchQueryUtils
                                .getSorts(searchQuery, historiqueSQMapper)
                                .stream()
                                .map(sort -> sort.getOrder(joinedContext.entreeJoin, criteriaBuilder))
                                .toList(),
                        SearchQueryUtils
                                .getSorts(searchQuery, historiqueSQMapper)
                                .stream()
                                .map(sort -> sort.getOrder(joinedContext.sortieJoin, criteriaBuilder))
                                .toList(),
                        havingOrders
                )
                .flatMap(Collection::stream)
                .toList());

        return entityManager
                .createQuery(query)
                .setFirstResult(searchQuery.getPage())
                .setMaxResults(searchQuery.getPageSize())
                .getResultList()
                .stream()
                .map(tuple -> new PieceWithHistoriquePojo(
                        ((PieceWithHistoriqueView) tuple.get(0)).toDomain(),
                        Optional.ofNullable((Long) tuple.get(1)).orElse(0L),
                        Optional.ofNullable((Long) tuple.get(2)).orElse(0L)
                ))
                .toList();
    }

    private JoinedContext applyJoinsAndAggregations(AbstractQuery<?> query,
                                                    CriteriaBuilder criteriaBuilder,
                                                    PieceWithHistoriqueSearchQuery searchQuery,
                                                    List<SearchQueryMapper<PieceWithHistoriqueSearchQuery, ?>> historiqueSQMapper) {
        Root<PieceWithHistoriqueView> pieceRoot = query.from(PieceWithHistoriqueView.class);
        Join<PieceWithHistoriqueView, PieceHistoriqueEntity> pieceHistoriqueEntreeRoot = pieceRoot.join("historique", JoinType.LEFT);
        Join<PieceWithHistoriqueView, PieceHistoriqueEntity> pieceHistoriqueSortieRoot = pieceRoot.join("historique", JoinType.LEFT);

        pieceHistoriqueEntreeRoot.on(
                criteriaBuilder.equal(
                        pieceHistoriqueEntreeRoot.get(PieceHistoriqueConstantes.FIELD_TYPE),
                        PieceHistoriqueType.ENTREE
                ),
                criteriaBuilder.and(FilterUtils.getPredicatesOfCombinaison(
                        SearchQueryUtils.getFilters(searchQuery, historiqueSQMapper),
                        pieceHistoriqueEntreeRoot,
                        criteriaBuilder
                ))
        );

        pieceHistoriqueSortieRoot.on(
                criteriaBuilder.equal(
                        pieceHistoriqueSortieRoot.get(PieceHistoriqueConstantes.FIELD_TYPE),
                        PieceHistoriqueType.SORTIE
                ),
                criteriaBuilder.and(FilterUtils.getPredicatesOfCombinaison(
                        SearchQueryUtils.getFilters(searchQuery, historiqueSQMapper),
                        pieceHistoriqueSortieRoot,
                        criteriaBuilder
                ))
        );

        Expression<Long> sumEntrees = criteriaBuilder.sum(pieceHistoriqueEntreeRoot.get(PieceHistoriqueConstantes.FIELD_DIFFERENCE));
        Expression<Long> sumSorties = criteriaBuilder.sum(pieceHistoriqueSortieRoot.get(PieceHistoriqueConstantes.FIELD_DIFFERENCE));

        List<Predicate> having = new ArrayList<>();

        if (Objects.nonNull(searchQuery.getQuantiteEntree())
            && Objects.nonNull(searchQuery.getQuantiteEntree().getValue())) {
            having.add(criteriaBuilder.equal(
                    sumEntrees,
                    searchQuery.getQuantiteEntree().getValue()
            ));
        }

        if (Objects.nonNull(searchQuery.getQuantiteSortie())
            && Objects.nonNull(searchQuery.getQuantiteSortie().getValue())) {
            having.add(criteriaBuilder.equal(
                    sumSorties,
                    searchQuery.getQuantiteSortie().getValue()
            ));
        }

        if (CollectionUtils.isNotEmpty(having)) {
            query.having(having.toArray(new Predicate[0]));
        }

        query.groupBy(pieceRoot);

        return new JoinedContext(
                pieceRoot,
                pieceHistoriqueEntreeRoot,
                pieceHistoriqueSortieRoot,
                sumEntrees,
                sumSorties
        );
    }

    @Override
    public List<Piece> searchAll(List<AutomaticFieldCombinator> filters) {
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
        Specification<PieceEntity> specification = (root, _, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(
                        criteriaBuilder.concat("",
                        criteriaBuilder.concat(root.get(PieceConstantes.FIELD_NUMERO_INVENTAIRE),
                        criteriaBuilder.concat(" / ",
                                               root.get(PieceConstantes.FIELD_NOM))))),
                        "%" + searchValue.toLowerCase() + "%");

        Pageable pageable = PaginationUtils.getDefaultPage(List.of(
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

    /**
     * Contexte des jointures pour la requête avec historique
     * @param root Chemin racine
     * @param entreeJoin Join des entrées
     * @param sortieJoin Join des sorties
     * @param sumEntrees Somme des entrées
     * @param sumSorties Somme des sorties
     */
    private record JoinedContext(
            Root<PieceWithHistoriqueView> root,
            Join<?, ?> entreeJoin,
            Join<?, ?> sortieJoin,
            Expression<Long> sumEntrees,
            Expression<Long> sumSorties
    ) {}
}
