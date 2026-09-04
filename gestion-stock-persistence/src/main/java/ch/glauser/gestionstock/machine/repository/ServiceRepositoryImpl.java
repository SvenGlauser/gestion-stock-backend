package ch.glauser.gestionstock.machine.repository;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.filters.searchquery.utils.AutomatedSearchQueryUtils;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.pagination.PageUtils;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.entity.ServiceEntity;
import ch.glauser.gestionstock.machine.model.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du repository de gestion des services
 */
@Repository
@RequiredArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private final ServiceJpaRepository serviceJpaRepository;

    @Override
    public Optional<Service> get(Long id) {
        return this.serviceJpaRepository.findById(id).map(ModelEntity::toDomain);
    }

    @Override
    public SearchResult<Service> search(AutomaticSearchQuery automaticSearchQuery) {
        Page<ServiceEntity> page = this.serviceJpaRepository.search(AutomatedSearchQueryUtils.getFiltersCombinators(automaticSearchQuery), AutomatedSearchQueryUtils.paginate(automaticSearchQuery));
        return PageUtils.transform(page);
    }

    @Override
    public List<Service> searchByMachine(Long idMachine) {
        return this.serviceJpaRepository
                .findAllByMachine(idMachine)
                .stream()
                .map(ServiceEntity::toDomain)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Service create(Service service) {
        return this.serviceJpaRepository.save(new ServiceEntity(service)).toDomain();
    }

    @Override
    public Service modify(Service service) {
        return this.serviceJpaRepository.save(new ServiceEntity(service)).toDomain();
    }

    @Override
    public void delete(Long id) {
        this.serviceJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByIdMachine(Long id) {
        return false;
    }
}
