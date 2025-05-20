package ch.glauser.gestionstock.machine.repository;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.repository.RepositoryUtils;
import ch.glauser.gestionstock.machine.entity.MachineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * JPA Repository pour la gestion des machines
 */
@Repository
public interface MachineJpaRepository extends JpaRepository<MachineEntity, Long>, JpaSpecificationExecutor<MachineEntity> {

    /**
     * Vérifie s'il existe une machine avec cette identité
     *
     * @param idProprietaire Id de l'identité
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(machine) > 0 FROM Machine machine WHERE machine.proprietaire.id = :id")
    boolean existsByIdProprietaire(@Param("id") Long idProprietaire);

    /**
     * Vérifie s'il existe une machine avec cette pièce
     *
     * @param id Id de la pièce
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(machine) > 0 FROM Machine machine JOIN machine.pieces piece WHERE piece.id = :id")
    boolean existsByIdPiece(@Param("id") Long id);

    /**
     * Vérifie s'il existe une machine avec cette identité et nom
     *
     * @param nom Nom de la pièce
     * @param idProprietaire Id de l'identité
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("""
            SELECT COUNT(machine) > 0 \
            FROM Machine machine \
            WHERE machine.proprietaire.id = :idProprietaire \
            AND machine.nom = :nom""")
    boolean existsByNomAndIdProprietaire(@Param("nom") String nom, @Param("idProprietaire") Long idProprietaire);

    default Page<MachineEntity> search(Collection<FilterCombinator> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
