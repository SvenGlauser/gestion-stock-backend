package ch.glauser.gestionstock.identite.repository;

import ch.glauser.gestionstock.identite.entity.PersonneMoraleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository pour la gestion des personnes morales
 */
@Repository
public interface PersonneMoraleJpaRepository extends JpaRepository<PersonneMoraleEntity, Long>, JpaSpecificationExecutor<PersonneMoraleEntity> {

}
