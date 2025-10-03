package ch.glauser.gestionstock.categorie.service;

import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.pagination.SearchResultUtils;
import ch.glauser.gestionstock.validation.common.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implémentation du service applicatif de gestion des catégories
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategorieApplicationServiceImpl implements CategorieApplicationService {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CATEGORIE = "categorie";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final CategorieService categorieService;

    @Override
    public CategorieDto get(Long id) {
        Validation.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        Categorie categorie = this.categorieService.get(id);

        return Optional.ofNullable(categorie).map(CategorieDto::new).orElse(null);
    }

    @Override
    public SearchResult<CategorieDto> search(SearchRequest searchRequest) {
        Validation.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        SearchResult<Categorie> searchResult = this.categorieService.search(searchRequest);

        return SearchResultUtils.transformDto(searchResult, CategorieDto::new);
    }

    @Override
    @Transactional
    public CategorieDto create(CategorieDto categorie) {
        Validation.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(categorie, FIELD_CATEGORIE)
                .execute();

        Categorie newCategorie = categorie.toDomain();

        Categorie savedCategorie = this.categorieService.create(newCategorie);

        return Optional.ofNullable(savedCategorie).map(CategorieDto::new).orElse(null);
    }

    @Override
    @Transactional
    public CategorieDto modify(CategorieDto categorie) {
        Validation.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(categorie, FIELD_CATEGORIE)
                .execute();

        Categorie categorieToUpdate = categorie.toDomain();

        Categorie savedCategorie = this.categorieService.modify(categorieToUpdate);

        return Optional.ofNullable(savedCategorie).map(CategorieDto::new).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Validation.of(CategorieApplicationServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        this.categorieService.delete(id);
    }
}
