package ch.glauser.gestionstock.identite.dto;

import ch.glauser.gestionstock.identite.model.PersonneMorale;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PersonneMoraleDto extends IdentiteDto {

    private String raisonSociale;

    public PersonneMoraleDto(PersonneMorale personneMorale) {
        super(personneMorale);
        this.raisonSociale = personneMorale.getRaisonSociale();
    }

    @Override
    protected PersonneMorale toDomainChild() {
        PersonneMorale personneMorale = new PersonneMorale();
        personneMorale.setRaisonSociale(Optional.ofNullable(this.raisonSociale).map(StringUtils::trimToNull).orElse(null));
        return personneMorale;
    }

    @Override
    public PersonneMorale toDomain() {
        return (PersonneMorale) super.toDomain();
    }
}
