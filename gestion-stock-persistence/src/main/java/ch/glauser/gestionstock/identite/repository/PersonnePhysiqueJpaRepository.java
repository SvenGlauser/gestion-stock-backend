package ch.glauser.gestionstock.identite.repository;

import ch.glauser.gestionstock.identite.entity.PersonnePhysiqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository pour la gestion des personnes physiques
 */
@Repository
public interface PersonnePhysiqueJpaRepository extends JpaRepository<PersonnePhysiqueEntity, Long>, JpaSpecificationExecutor<PersonnePhysiqueEntity> {

}
