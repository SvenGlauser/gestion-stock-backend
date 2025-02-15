package ch.glauser.gestionstock.localite.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.Validator;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des localités
 */
@RequiredArgsConstructor
public class LocaliteServiceImpl implements LocaliteService {

    public static final String FIELD_LOCALITE = "localite";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final LocaliteRepository localiteRepository;

    @Override
    public Localite getLocalite(Long id) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.localiteRepository.getLocalite(id);
    }

    @Override
    public SearchResult<Localite> searchLocalite(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.localiteRepository.searchLocalite(searchRequest);
    }

    @Override
    public Localite createLocalite(Localite localite) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(localite, FIELD_LOCALITE)
                .execute();

        localite.validate();

        return this.localiteRepository.createLocalite(localite);
    }

    @Override
    public Localite modifyLocalite(Localite localite) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(localite, FIELD_LOCALITE)
                .execute();

        localite.validateModify();

        return this.localiteRepository.modifyLocalite(localite);
    }

    @Override
    public void deleteLocalite(Long id) {
        Validator.of(LocaliteServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.localiteRepository.deleteLocalite(id);
    }
}
