package ch.glauser.gestionstock.identite.repository;

import ch.glauser.gestionstock.identite.entity.PersonneMoraleEntity;
import ch.glauser.gestionstock.identite.model.PersonneMorale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implémentation du repository de gestion des personnes morales
 */
@Repository
@RequiredArgsConstructor
public class PersonneMoraleRepositoryImpl implements PersonneMoraleRepository {

    private final PersonneMoraleJpaRepository personneMoraleJpaRepository;

    @Override
    public Optional<PersonneMorale> get(Long id) {
        return this.personneMoraleJpaRepository.findById(id).map(PersonneMoraleEntity::toDomain);
    }

    @Override
    public PersonneMorale create(PersonneMorale personneMorale) {
        return this.personneMoraleJpaRepository.save(new PersonneMoraleEntity(personneMorale)).toDomain();
    }

    @Override
    public PersonneMorale modify(PersonneMorale personneMorale) {
        return this.personneMoraleJpaRepository.save(new PersonneMoraleEntity(personneMorale)).toDomain();
    }

    @Override
    public void delete(Long id) {
        this.personneMoraleJpaRepository.deleteById(id);
    }
}
