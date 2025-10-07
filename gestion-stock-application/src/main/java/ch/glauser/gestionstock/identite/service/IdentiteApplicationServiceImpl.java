package ch.glauser.gestionstock.identite.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.identite.dto.IdentiteLightDto;
import ch.glauser.gestionstock.identite.model.Identite;
import ch.glauser.gestionstock.identite.model.PersonnePhysiqueConstantes;
import ch.glauser.gestionstock.validation.common.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation du service applicatif de gestion des personnes morales
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IdentiteApplicationServiceImpl implements IdentiteApplicationService {

    private final IdentiteService identiteService;

    @Override
    public SearchResult<IdentiteLightDto> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, PersonnePhysiqueConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Identite> searchResult = this.identiteService.search(searchRequest);

        return SearchResultUtils.transformDto(searchResult, IdentiteLightDto::new);
    }
}
