package ch.glauser.gestionstock.machine.service;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.ModifyWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.machine.model.Service;
import ch.glauser.gestionstock.machine.model.ServiceConstantes;
import ch.glauser.gestionstock.machine.repository.ServiceRepository;
import ch.glauser.validation.common.Validation;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Implémentation du service de gestion des services
 */
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public Service get(Long id) {
        Validation.of(ServiceServiceImpl.class)
                .validateNotNull(id, ServiceConstantes.FIELD_ID)
                .execute();

        return this.serviceRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, Service.class));
    }

    @Override
    public SearchResult<Service> search(AutomaticSearchQuery automaticSearchQuery) {
        Validation.of(ServiceServiceImpl.class)
                .validateNotNull(automaticSearchQuery, ServiceConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.serviceRepository.search(automaticSearchQuery);
    }

    @Override
    public List<Service> searchByMachine(Long idMachine) {
        Validation.of(ServiceServiceImpl.class)
                .validateNotNull(idMachine, ServiceConstantes.FIELD_ID_MACHINE)
                .execute();

        return this.serviceRepository.searchByMachine(idMachine);
    }

    @Override
    public Service create(Service service) {
        Validation.of(ServiceServiceImpl.class)
                .validateNotNull(service, ServiceConstantes.FIELD_SERVICE)
                .execute();

        service.validateCreate().execute();

        return this.serviceRepository.create(service);
    }

    @Override
    public Service modify(Service service) {
        Validation.of(ServiceServiceImpl.class)
                .validateNotNull(service, ServiceConstantes.FIELD_SERVICE)
                .execute();

        Service oldService = this.serviceRepository
                .get(service.getId())
                .orElseThrow(() -> new ModifyWithInexistingIdException(service.getId(), Service.class));

        Validation validation = service.validateModify();

        Long idMachine = Optional.ofNullable(service.getMachine()).map(Model::getId).orElse(null);

        if (!Objects.equals(idMachine, oldService.getMachine().getId())) {
            validation.addError(ServiceConstantes.ERROR_IMPOSSIBLE_CHANGER_MACHINE_SERVICE, ServiceConstantes.FIELD_MACHINE);
        }

        validation.execute();

        return this.serviceRepository.modify(service);
    }

    @Override
    public void delete(Long id) {
        Validation.of(ServiceServiceImpl.class)
                .validateNotNull(id, ServiceConstantes.FIELD_ID)
                .execute();

        this.serviceRepository
                .get(id)
                .orElseThrow(() -> new DeleteWithInexistingIdException(id, Service.class));

        this.serviceRepository.delete(id);
    }
}
