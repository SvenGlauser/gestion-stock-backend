package ch.glauser.gestionstock.machine.service;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.machine.dto.ServiceDto;
import ch.glauser.gestionstock.machine.model.Service;
import ch.glauser.validation.common.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du service applicatif de gestion des services
 */
@org.springframework.stereotype.Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceApplicationServiceImpl implements ServiceApplicationService {

    public static final String FIELD_SERVICE = "service";
    public static final String FIELD_ID = "id";
    public static final String FIELD_ID_MACHINE = "idMachine";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final ServiceService serviceService;

    @Override
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).SERVICE_LECTEUR.name())")
    public ServiceDto get(Long id) {
        Validation.of(ServiceApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Service service = this.serviceService.get(id);

        return Optional.ofNullable(service).map(ServiceDto::new).orElse(null);
    }

    @Override
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).SERVICE_LECTEUR.name())")
    public SearchResult<ServiceDto> search(AutomaticSearchQuery automaticSearchQuery) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(automaticSearchQuery, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Service> searchResult = this.serviceService.search(automaticSearchQuery);

        return SearchResultUtils.transformDto(searchResult, ServiceDto::new);
    }

    @Override
    public List<ServiceDto> searchByMachine(Long idMachine) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(idMachine, FIELD_ID_MACHINE)
                .execute();

        return this.serviceService
                .searchByMachine(idMachine)
                .stream()
                .map(ServiceDto::new)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).SERVICE_EDITEUR.name())")
    public ServiceDto create(ServiceDto service) {
        Validation.of(ServiceApplicationServiceImpl.class)
                .validateNotNull(service, FIELD_SERVICE)
                .execute();

        Service newService = service.toDomain();

        Service savedService = this.serviceService.create(newService);

        return Optional.ofNullable(savedService).map(ServiceDto::new).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).SERVICE_EDITEUR.name())")
    public ServiceDto modify(ServiceDto service) {
        Validation.of(ServiceApplicationServiceImpl.class)
                .validateNotNull(service, FIELD_SERVICE)
                .execute();

        Service serviceToUpdate = service.toDomain();

        Service savedService = this.serviceService.modify(serviceToUpdate);

        return Optional.ofNullable(savedService).map(ServiceDto::new).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).SERVICE_EDITEUR.name())")
    public void delete(Long id) {
        Validation.of(ServiceApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.serviceService.delete(id);
    }
}
