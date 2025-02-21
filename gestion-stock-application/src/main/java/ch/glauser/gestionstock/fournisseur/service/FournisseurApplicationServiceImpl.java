package ch.glauser.gestionstock.fournisseur.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des fournisseurs
 */
@Service
@RequiredArgsConstructor
public class FournisseurApplicationServiceImpl implements FournisseurApplicationService {

    public static final String FIELD_FOURNISSEUR = "fournisseur";
    public static final String FIELD_ID = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final FournisseurService fournisseurService;

    @Override
    public FournisseurDto getFournisseur(Long id) {
        Validator.of(FournisseurApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Fournisseur fournisseur = this.fournisseurService.getFournisseur(id);

        return Optional.ofNullable(fournisseur).map(FournisseurDto::new).orElse(null);
    }

    @Override
    public SearchResult<FournisseurDto> searchFournisseur(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Fournisseur> searchResult = this.fournisseurService.searchFournisseur(searchRequest);

        return SearchResultUtils.transformDto(searchResult, FournisseurDto::new);
    }

    @Override
    public FournisseurDto createFournisseur(FournisseurDto fournisseur) {
        Validator.of(FournisseurApplicationServiceImpl.class)
                .validateNotNull(fournisseur, FIELD_FOURNISSEUR)
                .execute();

        Fournisseur newFournisseur = fournisseur.toDomain();

        newFournisseur.validateCreate();

        Fournisseur savedFournisseur = this.fournisseurService.createFournisseur(newFournisseur);

        return Optional.ofNullable(savedFournisseur).map(FournisseurDto::new).orElse(null);
    }

    @Override
    public FournisseurDto modifyFournisseur(FournisseurDto fournisseur) {
        Validator.of(FournisseurApplicationServiceImpl.class)
                .validateNotNull(fournisseur, FIELD_FOURNISSEUR)
                .execute();

        Fournisseur fournisseurToUpdate = fournisseur.toDomain();

        fournisseurToUpdate.validateModify();

        Fournisseur savedFournisseur = this.fournisseurService.modifyFournisseur(fournisseurToUpdate);

        return Optional.ofNullable(savedFournisseur).map(FournisseurDto::new).orElse(null);
    }

    @Override
    public void deleteFournisseur(Long id) {
        Validator.of(FournisseurApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.fournisseurService.deleteFournisseur(id);
    }
}
