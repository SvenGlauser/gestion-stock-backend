package ch.glauser.gestionstock.pays.service;

import ch.glauser.gestionstock.categorie.service.CategorieServiceImpl;
import ch.glauser.gestionstock.common.exception.id.DeleteWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.ModifyWithInexistingIdException;
import ch.glauser.gestionstock.common.exception.id.PerformActionWithInexistingIdFunction;
import ch.glauser.gestionstock.common.exception.id.SearchWithInexistingIdExceptionPerform;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.model.PaysConstantes;
import ch.glauser.gestionstock.pays.repository.PaysRepository;
import ch.glauser.validation.common.Error;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.exception.ValidationException;
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
    public Pays get(Long id) {
        Validation.of(PaysServiceImpl.class)
                .validateNotNull(id, PaysConstantes.FIELD_ID)
                .execute();

        return this.paysRepository
                .get(id)
                .orElseThrow(() -> new SearchWithInexistingIdExceptionPerform(id, Pays.class));
    }

    @Override
    public Pays getByAbreviation(String abreviation) {
        Validation.of(PaysServiceImpl.class)
                .validateNotNull(abreviation, PaysConstantes.FIELD_ABREVIATION)
                .execute();

        return this.paysRepository.getByAbreviation(abreviation);
    }

    @Override
    public SearchResult<Pays> search(SearchRequest searchRequest) {
        Validation.of(CategorieServiceImpl.class)
                .validateNotNull(searchRequest, PaysConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.paysRepository.search(searchRequest);
    }

    @Override
    public Pays create(Pays pays) {
        Validation.of(PaysServiceImpl.class)
                .validateNotNull(pays, PaysConstantes.FIELD_PAYS)
                .execute();

        Validation validation = pays.validateCreate();

        if (this.paysRepository.existByNom(pays.getNom())) {
            validation.addError(PaysConstantes.ERROR_PAYS_NOM_UNIQUE, PaysConstantes.FIELD_NOM);
        }

        if (this.paysRepository.existByAbreviation(pays.getAbreviation())) {
            validation.addError(PaysConstantes.ERROR_PAYS_ABREVIATION_UNIQUE, PaysConstantes.FIELD_ABREVIATION);
        }

        validation.execute();

        return this.paysRepository.create(pays);
    }

    @Override
    public Pays modify(Pays pays) {
        Validation.of(PaysServiceImpl.class)
                .validateNotNull(pays, PaysConstantes.FIELD_PAYS)
                .execute();

        Pays oldPays = this.paysRepository
                .get(pays.getId())
                .orElseThrow(() -> new ModifyWithInexistingIdException(pays.getId(), Pays.class));

        Validation validation = pays.validateModify();

        if (!Objects.equals(oldPays.getNom(), pays.getNom()) &&
            this.paysRepository.existByNom(pays.getNom())) {
            validation.addError(PaysConstantes.ERROR_PAYS_NOM_UNIQUE, PaysConstantes.FIELD_NOM);
        }

        if (!Objects.equals(oldPays.getAbreviation(), pays.getAbreviation()) &&
            this.paysRepository.existByAbreviation(pays.getAbreviation())) {
            validation.addError(PaysConstantes.ERROR_PAYS_ABREVIATION_UNIQUE, PaysConstantes.FIELD_ABREVIATION);
        }

        validation.execute();

        return this.paysRepository.modify(pays);
    }

    @Override
    public void delete(Long id) {
        Validation.of(PaysServiceImpl.class)
                .validateNotNull(id, PaysConstantes.FIELD_ID)
                .execute();

        this.validateExist(id, DeleteWithInexistingIdException::new);
        this.validatePasUtiliseParLocalite(id);

        this.paysRepository.delete(id);
    }

    /**
     * Valide que le pays existe
     * @param id Id du pays à supprimer
     * @param exception L'exception à instancier
     */
    private void validateExist(Long id, PerformActionWithInexistingIdFunction exception) {
        this.paysRepository
                .get(id)
                .orElseThrow(() -> exception.instantiate(id, Pays.class));
    }

    /**
     * Valide que la localité n'est pas utilisé par un fournisseur
     * @param id Id de la localité à supprimer
     */
    private void validatePasUtiliseParLocalite(Long id) {
        if (this.localiteRepository.existByIdPays(id)) {
            throw new ValidationException(new Error(
                    PaysConstantes.ERROR_SUPPRESSION_PAYS_IMPOSSIBLE_EXISTE_LOCALITE,
                    PaysConstantes.FIELD_PAYS,
                    PaysServiceImpl.class));
        }
    }
}
