package ch.glauser.gestionstock.localite.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.localite.dto.LocaliteDto;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.validation.common.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des localités
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocaliteApplicationServiceImpl implements LocaliteApplicationService {

    public static final String FIELD_LOCALITE = "localite";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final LocaliteService localiteService;

    @Override
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).LOCALITE_LECTEUR.name())")
    public LocaliteDto get(Long id) {
        Validation.of(LocaliteApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Localite localite = this.localiteService.get(id);

        return Optional.ofNullable(localite).map(LocaliteDto::new).orElse(null);
    }

    @Override
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).LOCALITE_LECTEUR.name())")
    public SearchResult<LocaliteDto> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Localite> searchResult = this.localiteService.search(searchRequest);

        return SearchResultUtils.transformDto(searchResult, LocaliteDto::new);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).LOCALITE_EDITEUR.name())")
    public LocaliteDto create(LocaliteDto localite) {
        Validation.of(LocaliteApplicationServiceImpl.class)
                .validateNotNull(localite, FIELD_LOCALITE)
                .execute();

        Localite newLocalite = localite.toDomain();

        Localite savedCategorie = this.localiteService.create(newLocalite);

        return Optional.ofNullable(savedCategorie).map(LocaliteDto::new).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).LOCALITE_EDITEUR.name())")
    public LocaliteDto modify(LocaliteDto localite) {
        Validation.of(LocaliteApplicationServiceImpl.class)
                .validateNotNull(localite, FIELD_LOCALITE)
                .execute();

        Localite localiteToUpdate = localite.toDomain();

        Localite savedCategorie = this.localiteService.modify(localiteToUpdate);

        return Optional.ofNullable(savedCategorie).map(LocaliteDto::new).orElse(null);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).LOCALITE_EDITEUR.name())")
    public void delete(Long id) {
        Validation.of(LocaliteApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.localiteService.delete(id);
    }
}
