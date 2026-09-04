package ch.glauser.gestionstock.machine.repository;

import ch.glauser.filters.filter.api.FilterCombinaison;
import ch.glauser.filters.filter.utils.FilterUtils;
import ch.glauser.gestionstock.machine.entity.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * JPA Repository pour la gestion des services effectués sur une machine
 */
@Repository
public interface ServiceJpaRepository extends JpaRepository<ServiceEntity, Long>, JpaSpecificationExecutor<ServiceEntity> {

    /**
     * Vérifie s'il existe un service avec cette machine
     *
     * @param id Id du service
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    @Query("""
            SELECT COUNT(service) > 0
            FROM Service service
            JOIN service.machine machine
            WHERE machine.id = :id""")
    boolean existsByIdMachine(@Param("id") Long id);

    /**
     * Recherche tous les services liés à cette machine
     * @param idMachine Id la machine
     * @return Une liste de service
     */
    @Query("""
           SELECT service
           FROM Service service
           WHERE service.machine.id = :idMachine""")
    List<ServiceEntity> findAllByMachine(@Param("idMachine") Long idMachine);

    default Page<ServiceEntity> search(Collection<FilterCombinaison> filters, Pageable pageable) {
        return findAll(FilterUtils.specificationOf(filters), pageable);
    }
}
