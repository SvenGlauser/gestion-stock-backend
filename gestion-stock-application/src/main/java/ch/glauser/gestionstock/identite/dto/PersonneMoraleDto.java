package ch.glauser.gestionstock.identite.dto;

import ch.glauser.gestionstock.identite.model.PersonneMorale;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonneMoraleDto extends IdentiteDto<PersonneMorale> {

    private String raisonSociale;

    public PersonneMoraleDto(PersonneMorale personneMorale) {
        super(personneMorale);
        this.raisonSociale = personneMorale.getRaisonSociale();
    }

    @Override
    protected PersonneMorale toDomainChild() {
        PersonneMorale personneMorale = new PersonneMorale();
        personneMorale.setRaisonSociale(this.raisonSociale);
        return personneMorale;
    }
}
