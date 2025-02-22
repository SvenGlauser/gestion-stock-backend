package ch.glauser.gestionstock.localite.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.localite.dto.LocaliteDto;
import ch.glauser.gestionstock.localite.model.Localite;
import lombok.RequiredArgsConstructor;
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
    public LocaliteDto getLocalite(Long id) {
        Validator.of(LocaliteApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Localite localite = this.localiteService.getLocalite(id);

        return Optional.ofNullable(localite).map(LocaliteDto::new).orElse(null);
    }

    @Override
    public SearchResult<LocaliteDto> searchLocalite(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Localite> searchResult = this.localiteService.searchLocalite(searchRequest);

        return SearchResultUtils.transformDto(searchResult, LocaliteDto::new);
    }

    @Override
    @Transactional
    public LocaliteDto createLocalite(LocaliteDto localite) {
        Validator.of(LocaliteApplicationServiceImpl.class)
                .validateNotNull(localite, FIELD_LOCALITE)
                .execute();

        Localite newLocalite = localite.toDomain();

        newLocalite.validateCreate();

        Localite savedCategorie = this.localiteService.createLocalite(newLocalite);

        return Optional.ofNullable(savedCategorie).map(LocaliteDto::new).orElse(null);
    }

    @Override
    @Transactional
    public LocaliteDto modifyLocalite(LocaliteDto localite) {
        Validator.of(LocaliteApplicationServiceImpl.class)
                .validateNotNull(localite, FIELD_LOCALITE)
                .execute();

        Localite localiteToUpdate = localite.toDomain();

        localiteToUpdate.validateModify();

        Localite savedCategorie = this.localiteService.modifyLocalite(localiteToUpdate);

        return Optional.ofNullable(savedCategorie).map(LocaliteDto::new).orElse(null);
    }

    @Override
    @Transactional
    public void deleteLocalite(Long id) {
        Validator.of(LocaliteApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.localiteService.deleteLocalite(id);
    }
}
