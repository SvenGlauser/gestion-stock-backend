package ch.glauser.gestionstock.pays.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.pays.dto.PaysDto;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.validation.common.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des pays
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaysApplicationServiceImpl implements PaysApplicationService {

    public static final String FIELD_PAYS = "pays";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final PaysService paysService;

    @Override
    public PaysDto get(Long id) {
        Validation.of(PaysApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Pays pays = this.paysService.get(id);

        return Optional.ofNullable(pays).map(PaysDto::new).orElse(null);
    }

    @Override
    public SearchResult<PaysDto> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Pays> searchResult = this.paysService.search(searchRequest);

        return SearchResultUtils.transformDto(searchResult, PaysDto::new);
    }

    @Override
    @Transactional
    public PaysDto create(PaysDto pays) {
        Validation.of(PaysApplicationServiceImpl.class)
                .validateNotNull(pays, FIELD_PAYS)
                .execute();

        Pays newPays = pays.toDomain();

        Pays savedPays = this.paysService.create(newPays);

        return Optional.ofNullable(savedPays).map(PaysDto::new).orElse(null);
    }

    @Override
    @Transactional
    public PaysDto modify(PaysDto pays) {
        Validation.of(PaysApplicationServiceImpl.class)
                .validateNotNull(pays, FIELD_PAYS)
                .execute();

        Pays paysToUpdate = pays.toDomain();

        Pays savedPays = this.paysService.modify(paysToUpdate);

        return Optional.ofNullable(savedPays).map(PaysDto::new).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Validation.of(PaysApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.paysService.delete(id);
    }
}
