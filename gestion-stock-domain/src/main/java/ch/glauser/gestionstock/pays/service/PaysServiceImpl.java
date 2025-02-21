package ch.glauser.gestionstock.pays.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.repository.PaysRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des payss
 */
@RequiredArgsConstructor
public class PaysServiceImpl implements PaysService {

    public static final String FIELD_PAYS = "pays";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final PaysRepository paysRepository;

    @Override
    public Pays getPays(Long id) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.paysRepository.getPays(id);
    }

    @Override
    public SearchResult<Pays> searchPays(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.paysRepository.searchPays(searchRequest);
    }

    @Override
    public Pays createPays(Pays pays) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(pays, FIELD_PAYS)
                .execute();

        pays.validate();

        return this.paysRepository.createPays(pays);
    }

    @Override
    public Pays modifyPays(Pays pays) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(pays, FIELD_PAYS)
                .execute();

        pays.validateModify();

        return this.paysRepository.modifyPays(pays);
    }

    @Override
    public void deletePays(Long id) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.paysRepository.deletePays(id);
    }
}
