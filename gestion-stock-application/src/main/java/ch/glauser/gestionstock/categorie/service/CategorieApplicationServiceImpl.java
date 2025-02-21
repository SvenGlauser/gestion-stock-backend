package ch.glauser.gestionstock.categorie.service;

import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.common.validation.common.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des catégories
 */
@Service
@RequiredArgsConstructor
public class CategorieApplicationServiceImpl implements CategorieApplicationService {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CATEGORIE = "categorie";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final CategorieService categorieService;

    @Override
    public CategorieDto getCategorie(Long id) {
        Validator.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Categorie categorie = this.categorieService.getCategorie(id);

        return Optional.ofNullable(categorie).map(CategorieDto::new).orElse(null);
    }

    @Override
    public SearchResult<CategorieDto> searchCategorie(SearchRequest searchRequest) {
        Validator.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Categorie> searchResult = this.categorieService.searchCategorie(searchRequest);

        return SearchResultUtils.transformDto(searchResult, CategorieDto::new);
    }

    @Override
    public CategorieDto createCategorie(CategorieDto categorie) {
        Validator.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(categorie, FIELD_CATEGORIE)
                .execute();

        Categorie newCategorie = categorie.toDomain();

        newCategorie.validate();

        Categorie savedCategorie = this.categorieService.createCategorie(newCategorie);

        return Optional.ofNullable(savedCategorie).map(CategorieDto::new).orElse(null);
    }

    @Override
    public CategorieDto modifyCategorie(CategorieDto categorie) {
        Validator.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(categorie, FIELD_CATEGORIE)
                .execute();

        Categorie categorieToUpdate = categorie.toDomain();

        categorieToUpdate.validateModify();

        Categorie savedCategorie = this.categorieService.modifyCategorie(categorieToUpdate);

        return Optional.ofNullable(savedCategorie).map(CategorieDto::new).orElse(null);
    }

    @Override
    public void deleteCategorie(Long id) {
        Validator.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.categorieService.deleteCategorie(id);
    }
}
