package ch.glauser.gestionstock.machine.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.entity.MachineEntity;
import ch.glauser.gestionstock.machine.model.Machine;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implémentation du repository de gestion des machines
 */
@Repository
public class MachineRepositoryImpl implements MachineRepository {

    private final MachineJpaRepository machineJpaRepository;

    public MachineRepositoryImpl(MachineJpaRepository machineJpaRepository) {
        this.machineJpaRepository = machineJpaRepository;
    }

    @Override
    public Optional<Machine> get(Long id) {
        return this.machineJpaRepository.findById(id).map(ModelEntity::toDomain);
    }

    @Override
    public SearchResult<Machine> search(SearchRequest searchRequest) {
        Page<MachineEntity> page = this.machineJpaRepository.search(PageUtils.getFiltersCombinators(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Machine create(Machine machine) {
        return this.machineJpaRepository.save(new MachineEntity(machine)).toDomain();
    }

    @Override
    public Machine modify(Machine machine) {
        return this.machineJpaRepository.save(new MachineEntity(machine)).toDomain();
    }

    @Override
    public void delete(Long id) {
        this.machineJpaRepository.deleteById(id);
    }

    @Override
    public boolean existByIdProprietaire(Long id) {
        return this.machineJpaRepository.existsByIdProprietaire(id);
    }

    @Override
    public boolean existByIdPiece(Long id) {
        return this.machineJpaRepository.existsByIdPiece(id);
    }

    @Override
    public boolean existByNomAndIdProprietaire(String nom, Long idProprietaire) {
        return this.machineJpaRepository.existsByNomAndIdProprietaire(nom, idProprietaire);
    }
}
