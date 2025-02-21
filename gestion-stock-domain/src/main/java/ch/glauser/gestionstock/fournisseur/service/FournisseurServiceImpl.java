package ch.glauser.gestionstock.fournisseur.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.gestionstock.fournisseur.repository.FournisseurRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implémentation du service de gestion des fournisseurs
 */
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {

    public static final String FIELD_FOURNISSEUR = "fournisseur";
    public static final String FIELD_ID = "id";
    public static final String FIELD_ID_LOCALITE = "id";
    public static final String FIELD_SEARCH_REQUEST = "searchRequest";

    private final FournisseurRepository fournisseurRepository;

    @Override
    public Fournisseur getFournisseur(Long id) {
        Validator.of(FournisseurServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        return this.fournisseurRepository.getFournisseur(id);
    }

    @Override
    public SearchResult<Fournisseur> searchFournisseur(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, FIELD_SEARCH_REQUEST)
                .execute();

        return this.fournisseurRepository.searchFournisseur(searchRequest);
    }

    @Override
    public Fournisseur createFournisseur(Fournisseur fournisseur) {
        Validator.of(FournisseurServiceImpl.class)
                .validateNotNull(fournisseur, FIELD_FOURNISSEUR)
                .execute();

        fournisseur.validate();

        return this.fournisseurRepository.createFournisseur(fournisseur);
    }

    @Override
    public Fournisseur modifyFournisseur(Fournisseur fournisseur) {
        Validator.of(FournisseurServiceImpl.class)
                .validateNotNull(fournisseur, FIELD_FOURNISSEUR)
                .execute();

        fournisseur.validateModify();

        return this.fournisseurRepository.modifyFournisseur(fournisseur);
    }

    @Override
    public void deleteFournisseur(Long id) {
        Validator.of(FournisseurServiceImpl.class)
                .validateNotNull(id, FIELD_ID)
                .execute();

        // FIXME delete impossible if piece

        this.fournisseurRepository.deleteFournisseur(id);
    }

    @Override
    public boolean existFournisseurWithIdLocalite(Long id) {
        Validator.of(Fournisseur.class)
                .validateNotNull(id, FIELD_ID_LOCALITE)
                .execute();

        return this.fournisseurRepository.existFournisseurWithIdLocalite(id);
    }
}
