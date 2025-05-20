package ch.glauser.gestionstock.identite.repository;

import ch.glauser.gestionstock.identite.entity.PersonnePhysiqueEntity;
import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implémentation du repository de gestion des personnes physiques
 */
@Repository
public class PersonnePhysiqueRepositoryImpl implements PersonnePhysiqueRepository {

    private final PersonnePhysiqueJpaRepository personnePhysiqueJpaRepository;

    public PersonnePhysiqueRepositoryImpl(PersonnePhysiqueJpaRepository personnePhysiqueJpaRepository) {
        this.personnePhysiqueJpaRepository = personnePhysiqueJpaRepository;
    }

    @Override
    public Optional<PersonnePhysique> get(Long id) {
        return this.personnePhysiqueJpaRepository.findById(id).map(PersonnePhysiqueEntity::toDomain);
    }

    @Override
    public PersonnePhysique create(PersonnePhysique personnePhysique) {
        return this.personnePhysiqueJpaRepository.save(new PersonnePhysiqueEntity(personnePhysique)).toDomain();
    }

    @Override
    public PersonnePhysique modify(PersonnePhysique personnePhysique) {
        return this.personnePhysiqueJpaRepository.save(new PersonnePhysiqueEntity(personnePhysique)).toDomain();
    }

    @Override
    public void delete(Long id) {
        this.personnePhysiqueJpaRepository.deleteById(id);
    }
}
