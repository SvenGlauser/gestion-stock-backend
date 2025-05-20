package ch.glauser.gestionstock.identite.entity;

import ch.glauser.gestionstock.identite.model.PersonneMorale;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "PersonneMorale")
public class PersonneMoraleEntity extends IdentiteEntity {
    @Column(name = "RAISON_SOCIALE", nullable = false)
    private String raisonSociale;

    public PersonneMoraleEntity(PersonneMorale personneMorale) {
        super(personneMorale);
        this.raisonSociale = personneMorale.getRaisonSociale();
    }

    @Override
    protected PersonneMorale toDomainChild() {
        PersonneMorale personneMorale = new PersonneMorale();
        personneMorale.setRaisonSociale(this.raisonSociale);
        return personneMorale;
    }

    @Override
    public PersonneMorale toDomain() {
        return (PersonneMorale) super.toDomain();
    }

    @Override
    protected String designation() {
        return this.raisonSociale;
    }
}
