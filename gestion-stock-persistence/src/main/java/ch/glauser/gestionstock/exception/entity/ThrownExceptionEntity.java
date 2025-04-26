package ch.glauser.gestionstock.exception.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.exception.model.ThrownException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Exception")
@Table(name = "EXCEPTION")
public class ThrownExceptionEntity extends ModelEntity<ThrownException> {
    @Column(name = "STACKTRACE", columnDefinition = "TEXT", nullable = false)
    private String stacktrace;
    @Column(name = "CLASS_NAME", columnDefinition = "TEXT", nullable = false)
    private String className;
    @Column(name = "MESSAGE", columnDefinition = "TEXT")
    private String message;
    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;
    @Column(name = "ACTIF", nullable = false)
    private boolean actif;

    public ThrownExceptionEntity(ThrownException exception) {
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
        thrownException.setStacktrace(this.stacktrace);
        thrownException.setClassName(this.className);
        thrownException.setMessage(this.message);
        thrownException.setTimestamp(this.timestamp);
        thrownException.setActif(this.actif);
        return thrownException;
    }
}
