package ch.glauser.gestionstock.machine.model;

import ch.glauser.gestionstock.common.model.Model;
import ch.glauser.validation.common.Validation;
import ch.glauser.validation.maxlength.MaxLength;
import ch.glauser.validation.minvalue.MinValue;
import ch.glauser.validation.notnull.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Modèle représentant un service qui a été effectué sur une machine
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Service extends Model {
    @NotNull
    private Machine machine;

    @NotNull
    private LocalDate date;

    @NotNull
    @MinValue(0)
    private Double duree;

    @MaxLength(4096)
    private String descriptionTravaux;

    @MaxLength(4096)
    private String divers;

    @Override
    protected Validation validateChild() {
        return Validation.validate(this, Service.class);
    }
}
