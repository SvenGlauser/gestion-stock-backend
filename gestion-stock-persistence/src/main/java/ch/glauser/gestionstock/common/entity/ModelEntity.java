package ch.glauser.gestionstock.common.entity;

import ch.glauser.gestionstock.common.model.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Common entity
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ModelEntity<T extends Model> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",
            nullable = false,
            updatable = false,
            unique = true)
    private Long id;

    @CreatedBy
    @Column(name = "CREATION_USER", updatable = false)
    private String creationUser;

    @CreatedDate
    @Column(name = "CREATION_DATE", updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedBy
    @Column(name = "MODIFICATION_USER")
    private String modificationUser;

    @LastModifiedDate
    @Column(name = "MODIFICATION_DATE")
    private LocalDateTime modificationDate;

    /**
     * Transform un model en entité
     *
     * @param entity Entité
     */
    protected ModelEntity(T entity) {
        this.id = entity.getId();
        this.creationUser = entity.getCreationUser();
        this.creationDate = entity.getCreationDate();
        this.modificationUser = entity.getModificationUser();
        this.modificationDate = entity.getModificationDate();
    }

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
     * Transforme l'entité en model
     * @return Le model
     */
    protected abstract T toDomainChild();
}
