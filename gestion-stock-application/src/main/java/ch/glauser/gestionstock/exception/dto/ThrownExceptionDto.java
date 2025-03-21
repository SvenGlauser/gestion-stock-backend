package ch.glauser.gestionstock.exception.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.exception.model.ThrownException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ThrownExceptionDto extends ModelDto<ThrownException> {
    private String stacktrace;
    private String className;
    private String message;
    private boolean actif;

    public ThrownExceptionDto(ThrownException exception) {
        super(exception);
        this.stacktrace = exception.getStacktrace();
        this.className = exception.getClassName();
        this.message = exception.getMessage();
        this.actif = exception.isActif();
    }

    @Override
    protected ThrownException toDomainChild() {
        ThrownException thrownException = new ThrownException();
        thrownException.setStacktrace(this.stacktrace);
        thrownException.setClassName(this.className);
        thrownException.setMessage(this.message);
        thrownException.setActif(this.actif);
        return thrownException;
    }
}
