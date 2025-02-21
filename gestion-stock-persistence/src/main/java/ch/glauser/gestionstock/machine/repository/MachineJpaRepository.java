package ch.glauser.gestionstock.machine.repository;

import ch.glauser.gestionstock.common.pagination.Filter;
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
import java.util.Optional;

/**
 * JPA Repository pour la gestion des machines
 */
@Repository
public interface MachineJpaRepository extends JpaRepository<MachineEntity, Long>, JpaSpecificationExecutor<MachineEntity> {
    /**
     * Récupère une machine par id
     *
     * @param id Id de la machine
     * @return Un {@link Optional} de {@link MachineEntity}
     */
    Optional<MachineEntity> findOptionalById(Long id);

    /**
     * Vérifie s'il existe une machine avec ce contact
     *
     * @param id Id du contact
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("SELECT COUNT(machine) > 0 FROM Machine machine WHERE machine.contact.id = :id")
    boolean existsByIdContact(@Param("id") Long id);

    default Page<MachineEntity> search(Collection<Filter> filters, Pageable pageable) {
        return findAll(RepositoryUtils.specificationOf(filters), pageable);
    }
}
