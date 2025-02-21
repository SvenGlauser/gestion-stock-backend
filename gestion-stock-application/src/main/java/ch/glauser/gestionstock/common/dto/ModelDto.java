package ch.glauser.gestionstock.common.dto;

import ch.glauser.gestionstock.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Common DTO
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class ModelDto<T extends Model> {
    private Long id;

    /**
     * Transform un model en DTO
     *
     * @param dto DTO
     */
    protected ModelDto(T dto) {
        this.id = dto.getId();
    }

    /**
     * Transforme la DTO dans le model correspondant
     *
     * @return Un model de type {@link T}
     */
    public T toDomain() {
        T model = this.toDomainChild();
        model.setId(this.id);
        return model;
    }

    /**
     * Transforme la DTO en model
     * @return Le model
     */
    protected abstract T toDomainChild();
}
