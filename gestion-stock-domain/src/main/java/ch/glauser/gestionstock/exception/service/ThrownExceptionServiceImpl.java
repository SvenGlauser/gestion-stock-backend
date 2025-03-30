package ch.glauser.gestionstock.exception.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.common.validation.common.Validation;
import ch.glauser.gestionstock.exception.model.ThrownException;
import ch.glauser.gestionstock.exception.model.ThrownExceptionConstantes;
import ch.glauser.gestionstock.exception.repository.ThrownExceptionRepository;
import lombok.RequiredArgsConstructor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Implémentation du service de gestion des exceptions
 */
@RequiredArgsConstructor
public class ThrownExceptionServiceImpl implements ThrownExceptionService {

    private final ThrownExceptionRepository thrownExceptionRepository;

    @Override
    public SearchResult<ThrownException> searchExceptions(SearchRequest searchRequest) {
        Validation.of(ThrownExceptionServiceImpl.class)
                .validateNotNull(searchRequest, ThrownExceptionConstantes.FIELD_SEARCH_REQUEST)
                .execute();

        return this.thrownExceptionRepository.searchExceptions(searchRequest);
    }

    @Override
    public ThrownException createException(Exception exception) {
        Validation.of(ThrownExceptionServiceImpl.class)
                .validateNotNull(exception, ThrownExceptionConstantes.FIELD_EXCEPTION)
                .execute();

        ThrownException thrownException = new ThrownException();

        // Récupère une jolie stacktrace
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);

        thrownException.setStacktrace(sw.toString());

        thrownException.setClassName(exception.getClass().getName());
        thrownException.setMessage(exception.getMessage());
        thrownException.setTimestamp(LocalDateTime.now());
        thrownException.setActif(true);

        return this.thrownExceptionRepository.createException(thrownException);
    }

    @Override
    public void changeStatus(Long id, Boolean actif) {
        Validation.of(ThrownExceptionServiceImpl.class)
                .validateNotNull(id, ThrownExceptionConstantes.FIELD_ID)
                .validateNotNull(actif, ThrownExceptionConstantes.FIELD_ACTIF)
                .execute();

        ThrownException thrownException = this.thrownExceptionRepository.getException(id);

        if (Objects.isNull(thrownException)) {
            return;
        }

        thrownException.setActif(actif);

        this.thrownExceptionRepository.modifyException(thrownException);
    }
}
