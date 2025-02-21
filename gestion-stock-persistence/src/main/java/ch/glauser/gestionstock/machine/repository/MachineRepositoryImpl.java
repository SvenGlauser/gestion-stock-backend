package ch.glauser.gestionstock.machine.repository;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.entity.MachineEntity;
import ch.glauser.gestionstock.machine.model.Machine;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

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
    public Machine getMachine(Long id) {
        return this.machineJpaRepository.findOptionalById(id).map(ModelEntity::toDomain).orElse(null);
    }

    @Override
    public SearchResult<Machine> searchMachine(SearchRequest searchRequest) {
        Page<MachineEntity> page = this.machineJpaRepository.search(PageUtils.getFilters(searchRequest), PageUtils.paginate(searchRequest));
        return PageUtils.transform(page);
    }

    @Override
    public Machine createMachine(Machine machine) {
        return this.machineJpaRepository.save(new MachineEntity(machine)).toDomain();
    }

    @Override
    public Machine modifyMachine(Machine machine) {
        return this.machineJpaRepository.save(new MachineEntity(machine)).toDomain();
    }

    @Override
    public void deleteMachine(Long id) {
        this.machineJpaRepository.deleteById(id);
    }

    @Override
    public boolean existMachineWithIdContact(Long id) {
        return this.machineJpaRepository.existsByIdContact(id);
    }

    @Override
    public boolean existMachineWithIdPiece(Long id) {
        return this.machineJpaRepository.existsByIdPiece(id);
    }
}
