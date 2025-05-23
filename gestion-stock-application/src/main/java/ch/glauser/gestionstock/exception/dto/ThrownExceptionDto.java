package ch.glauser.gestionstock.exception.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.exception.model.ThrownException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ThrownExceptionDto extends ModelDto<ThrownException> {
    private String stacktrace;
    private String className;
    private String message;
    private LocalDateTime timestamp;
    private boolean actif;

    public ThrownExceptionDto(ThrownException exception) {
        super(exception);
        this.stacktrace = exception.getStacktrace();
        this.className = exception.getClassName();
        this.message = exception.getMessage();
        this.timestamp = exception.getTimestamp();
        this.actif = exception.isActif();
    }

    @Override
    protected ThrownException toDomainChild() {
        ThrownException thrownException = new ThrownException();
        thrownException.setStacktrace(Optional.ofNullable(this.stacktrace).map(StringUtils::trimToNull).orElse(null));
        thrownException.setClassName(Optional.ofNullable(this.className).map(StringUtils::trimToNull).orElse(null));
        thrownException.setMessage(Optional.ofNullable(this.message).map(StringUtils::trimToNull).orElse(null));
        thrownException.setTimestamp(this.timestamp);
        thrownException.setActif(this.actif);
        return thrownException;
    }
}
