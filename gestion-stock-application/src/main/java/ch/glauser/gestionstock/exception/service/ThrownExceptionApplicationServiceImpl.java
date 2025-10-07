package ch.glauser.gestionstock.exception.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.exception.dto.ThrownExceptionDto;
import ch.glauser.gestionstock.exception.model.ThrownException;
import ch.glauser.gestionstock.exception.model.ThrownExceptionConstantes;
import ch.glauser.validation.common.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implémentation du service de gestion des exceptions
 */
@Service
@RequiredArgsConstructor
public class ThrownExceptionApplicationServiceImpl implements ThrownExceptionApplicationService {

    private final ThrownExceptionService thrownExceptionService;

    @Override
    public SearchResult<ThrownExceptionDto> search(SearchRequest searchRequest) {
        Validation.of(ThrownExceptionApplicationServiceImpl.class)
                .validateNotNull(searchRequest, ThrownExceptionConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<ThrownException> searchResult = this.thrownExceptionService.searchExceptions(searchRequest);

        return SearchResultUtils.transformDto(searchResult, ThrownExceptionDto::new);
    }

    @Override
    public void changeStatus(Long id, Boolean actif) {
        Validation.of(ThrownExceptionApplicationServiceImpl.class)
                .validateNotNull(id, ThrownExceptionConstantes.FIELD_ID)
                .validateNotNull(actif, ThrownExceptionConstantes.FIELD_ACTIF)
                .execute();

        this.thrownExceptionService.changeStatus(id, actif);
    }
}
