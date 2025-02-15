package ch.glauser.gestionstock.common.entity;

import ch.glauser.gestionstock.common.model.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Common entity
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class ModelEntity<T extends Model> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",
            nullable = false,
            updatable = false,
            unique = true)
    private Long id;

    /**
     * Transform un model en entité
     *
     * @param entity Entité
     */
    protected ModelEntity(T entity) {
        this.id = entity.getId();
    }

    /**
     * Transforme l'entité dans le model correspondant
     *
     * @return Un model de type {@link T}
     */
    public T toDomain() {
        T model = this.toDomainChild();
        model.setId(this.id);
        return model;
    }

    /**
     * Transforme l'entité en model
     * @return Le model
     */
    protected abstract T toDomainChild();
}
