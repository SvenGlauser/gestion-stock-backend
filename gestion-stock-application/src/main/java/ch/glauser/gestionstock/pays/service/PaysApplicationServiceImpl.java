package ch.glauser.gestionstock.pays.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.pays.dto.PaysDto;
import ch.glauser.gestionstock.pays.model.Pays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des pays
 */
@Service
@RequiredArgsConstructor
public class PaysApplicationServiceImpl implements PaysApplicationService {

    public static final String FIELD_PAYS = "pays";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final PaysService paysService;

    @Override
    public PaysDto getPays(Long id) {
        Validator.of(PaysApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Pays pays = this.paysService.getPays(id);

        return Optional.ofNullable(pays).map(PaysDto::new).orElse(null);
    }

    @Override
    public SearchResult<PaysDto> searchPays(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Pays> searchResult = this.paysService.searchPays(searchRequest);

        return SearchResultUtils.transformDto(searchResult, PaysDto::new);
    }

    @Override
    public PaysDto createPays(PaysDto pays) {
        Validator.of(PaysApplicationServiceImpl.class)
                .validateNotNull(pays, FIELD_PAYS)
                .execute();

        Pays newPays = pays.toDomain();

        newPays.validateCreate();

        Pays savedPays = this.paysService.createPays(newPays);

        return Optional.ofNullable(savedPays).map(PaysDto::new).orElse(null);
    }

    @Override
    public PaysDto modifyPays(PaysDto pays) {
        Validator.of(PaysApplicationServiceImpl.class)
                .validateNotNull(pays, FIELD_PAYS)
                .execute();

        Pays paysToUpdate = pays.toDomain();

        paysToUpdate.validateModify();

        Pays savedPays = this.paysService.modifyPays(paysToUpdate);

        return Optional.ofNullable(savedPays).map(PaysDto::new).orElse(null);
    }

    @Override
    public void deletePays(Long id) {
        Validator.of(PaysApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.paysService.deletePays(id);
    }
}
