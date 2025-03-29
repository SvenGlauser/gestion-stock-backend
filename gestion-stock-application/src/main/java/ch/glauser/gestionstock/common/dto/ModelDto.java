package ch.glauser.gestionstock.common.dto;

import ch.glauser.gestionstock.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Common DTO
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class ModelDto<T extends Model> {
    private Long id;
    private String creationUser;
    private LocalDateTime creationDate;
    private String modificationUser;
    private LocalDateTime modificationDate;

    /**
     * Transform un model en DTO
     *
     * @param dto Entité
     */
    protected ModelDto(T dto) {
        this.id = dto.getId();
        this.creationUser = dto.getCreationUser();
        this.creationDate = dto.getCreationDate();
        this.modificationUser = dto.getModificationUser();
        this.modificationDate = dto.getModificationDate();
    }

    /**
     * Transforme la DTO dans le model correspondant
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
     * Transforme la DTO en model
     * @return Le model
     */
    protected abstract T toDomainChild();
}
