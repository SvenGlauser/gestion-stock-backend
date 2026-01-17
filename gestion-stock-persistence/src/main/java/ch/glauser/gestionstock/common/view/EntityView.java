package ch.glauser.gestionstock.common.view;

import ch.glauser.gestionstock.common.model.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

/**
 * View non mutable de base
 */
@Getter
@NoArgsConstructor
@Immutable
@MappedSuperclass
public abstract class EntityView<T extends Model> {
    @Id
    @Column(name = "ID",
            nullable = false,
            updatable = false,
            unique = true)
    private Long id;

    @Column(name = "CREATION_USER", updatable = false)
    private String creationUser;

    @Column(name = "CREATION_DATE", updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "MODIFICATION_USER", updatable = false)
    private String modificationUser;

    @Column(name = "MODIFICATION_DATE", updatable = false)
    private LocalDateTime modificationDate;

    /**
     * Transforme l'entité dans le model correspondant
     *
     * @return Un model de type {@link T}
     */
    public T toDomain() {
        T model = this.toDomainChild();
        model.setId(this.id);
        model.setCreationUser(this.creationUser);
        model.setCreationDate(this.creationDate);
        model.setModificationUser(this.modificationUser);
        model.setModificationDate(this.modificationDate);
        return model;
    }

    /**
     * Transforme la vue en model
     * @return Le model
     */
    protected abstract T toDomainChild();
}
