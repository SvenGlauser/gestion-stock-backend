package ch.glauser.gestionstock.piece.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * JPA Repository pour la gestion des pieces
 */
@Repository
public interface PieceJpaRepository extends JpaRepository<PieceEntity, Long>, JpaSpecificationExecutor<PieceEntity> {
    /**
     * Récupère une pièce par id
     *
     * @param id Id de la pièce
     * @return Un {@link Optional} de {@link PieceEntity}
     */
    Optional<PieceEntity> findOptionalById(Long id);

    /**
     * Vérifie s'il existe une pièce avec cette catégorie
     *
     * @param id Id de la catégorie
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(piece) > 0 FROM Piece piece WHERE piece.categorie.id = :id")
    boolean existsByIdCategorie(@Param("id") Long id);

    /**
     * Vérifie s'il existe une pièce avec ce fournisseur
     *
     * @param id Id du fournisseur
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(piece) > 0 FROM Piece piece WHERE piece.fournisseur.id = :id")
    boolean existsByIdFournisseur(@Param("id") Long id);

    /**
     * Vérifie s'il existe une pièce avec ce nom
     *
     * @param nom Nom de la catégorie
     * @return {@code true} s'il en existe une, sinon {@code false}
     */
    boolean existsByNom(String nom);

    default Page<PieceEntity> search(Collection<Filter> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
