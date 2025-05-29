package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceConstantes;
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
public class PieceRepositoryImpl implements PieceRepository {

    private final PieceJpaRepository pieceJpaRepository;

    public PieceRepositoryImpl(PieceJpaRepository pieceJpaRepository) {
        this.pieceJpaRepository = pieceJpaRepository;
    }

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
