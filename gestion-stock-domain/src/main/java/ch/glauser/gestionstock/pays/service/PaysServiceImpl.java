package ch.glauser.gestionstock.pays.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Error;
import ch.glauser.gestionstock.common.validation.common.Validator;
import ch.glauser.gestionstock.common.validation.exception.ValidationException;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.model.PaysConstantes;
import ch.glauser.gestionstock.pays.repository.PaysRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Implémentation du service de gestion des payss
 */
@RequiredArgsConstructor
public class PaysServiceImpl implements PaysService {

    private final PaysRepository paysRepository;

    private final LocaliteRepository localiteRepository;

    @Override
    public Pays getPays(Long id) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(id, PaysConstantes.FIELD_ID)
                .execute();

        return this.paysRepository.getPays(id);
    }

    @Override
    public Pays getPaysByAbreviation(String abreviation) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(abreviation, PaysConstantes.FIELD_ABREVIATION)
                .execute();

        return this.paysRepository.getPaysByAbreviation(abreviation);
    }

    @Override
    public SearchResult<Pays> searchPays(SearchRequest searchRequest) {
        Validator.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, PaysConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.paysRepository.searchPays(searchRequest);
    }

    @Override
    public Pays createPays(Pays pays) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(pays, PaysConstantes.FIELD_PAYS)
                .execute();

        Validator validator = pays.validateCreate();

        if (this.paysRepository.existPaysByNom(pays.getNom())) {
            validator.addError(PaysConstantes.ERROR_PAYS_NOM_UNIQUE, PaysConstantes.FIELD_NOM);
        }

        if (this.paysRepository.existPaysByAbreviation(pays.getAbreviation())) {
            validator.addError(PaysConstantes.ERROR_PAYS_ABREVIATION_UNIQUE, PaysConstantes.FIELD_ABREVIATION);
        }

        validator.execute();

        return this.paysRepository.createPays(pays);
    }

    @Override
    public Pays modifyPays(Pays pays) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(pays, PaysConstantes.FIELD_PAYS)
                .execute();

        Pays oldPays = this.paysRepository.getPays(pays.getId());

        Validator validator = pays.validateModify();

        if (Objects.nonNull(oldPays)) {
            if (!Objects.equals(oldPays.getNom(), pays.getNom()) &&
                this.paysRepository.existPaysByNom(pays.getNom())) {
                validator.addError(PaysConstantes.ERROR_PAYS_NOM_UNIQUE, PaysConstantes.FIELD_NOM);
            }

            if (!Objects.equals(oldPays.getAbreviation(), pays.getAbreviation()) &&
                this.paysRepository.existPaysByAbreviation(pays.getAbreviation())) {
                validator.addError(PaysConstantes.ERROR_PAYS_ABREVIATION_UNIQUE, PaysConstantes.FIELD_ABREVIATION);
            }
        }

        validator.execute();

        return this.paysRepository.modifyPays(pays);
    }

    @Override
    public void deletePays(Long id) {
        Validator.of(PaysServiceImpl.class)
                .validateNotNull(id, PaysConstantes.FIELD_ID)
                .execute();

        this.validatePaysExist(id);
        this.validatePasUtiliseParLocalite(id);

        this.paysRepository.deletePays(id);
    }

    /**
     * Valide que le pays existe
     * @param id Id du pays à supprimer
     */
    private void validatePaysExist(Long id) {
        Pays paysToDelete = this.getPays(id);

        if (Objects.isNull(paysToDelete)) {
            throw new ValidationException(new Error(
                    PaysConstantes.ERROR_SUPPRESSION_PAYS_INEXISTANTE,
                    PaysConstantes.FIELD_PAYS,
                    PaysServiceImpl.class));
        }
    }

    /**
     * Valide que la localité n'est pas utilisé par un fournisseur
     * @param id Id de la localité à supprimer
     */
    private void validatePasUtiliseParLocalite(Long id) {
        if (this.localiteRepository.existLocaliteByIdPays(id)) {
            throw new ValidationException(new Error(
                    PaysConstantes.ERROR_SUPPRESSION_PAYS_IMPOSSIBLE_EXISTE_LOCALITE,
                    PaysConstantes.FIELD_PAYS,
                    PaysServiceImpl.class));
        }
    }
}
