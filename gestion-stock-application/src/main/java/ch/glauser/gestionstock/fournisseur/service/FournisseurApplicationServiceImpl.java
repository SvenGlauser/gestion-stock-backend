package ch.glauser.gestionstock.fournisseur.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.validation.common.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des fournisseurs
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FournisseurApplicationServiceImpl implements FournisseurApplicationService {

    public static final String FIELD_FOURNISSEUR = "fournisseur";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final FournisseurService fournisseurService;

    @Override
    public FournisseurDto get(Long id) {
        Validation.of(FournisseurApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Fournisseur fournisseur = this.fournisseurService.get(id);

        return Optional.ofNullable(fournisseur).map(FournisseurDto::new).orElse(null);
    }

    @Override
    public SearchResult<FournisseurDto> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Fournisseur> searchResult = this.fournisseurService.search(searchRequest);

        return SearchResultUtils.transformDto(searchResult, FournisseurDto::new);
    }

    @Override
    @Transactional
    public FournisseurDto create(FournisseurDto fournisseur) {
        Validation.of(FournisseurApplicationServiceImpl.class)
                .validateNotNull(fournisseur, FIELD_FOURNISSEUR)
                .execute();

        Fournisseur newFournisseur = fournisseur.toDomain();

        Fournisseur savedFournisseur = this.fournisseurService.create(newFournisseur);

        return Optional.ofNullable(savedFournisseur).map(FournisseurDto::new).orElse(null);
    }

    @Override
    @Transactional
    public FournisseurDto modify(FournisseurDto fournisseur) {
        Validation.of(FournisseurApplicationServiceImpl.class)
                .validateNotNull(fournisseur, FIELD_FOURNISSEUR)
                .execute();

        Fournisseur fournisseurToUpdate = fournisseur.toDomain();

        Fournisseur savedFournisseur = this.fournisseurService.modify(fournisseurToUpdate);

        return Optional.ofNullable(savedFournisseur).map(FournisseurDto::new).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Validation.of(FournisseurApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.fournisseurService.delete(id);
    }
}
