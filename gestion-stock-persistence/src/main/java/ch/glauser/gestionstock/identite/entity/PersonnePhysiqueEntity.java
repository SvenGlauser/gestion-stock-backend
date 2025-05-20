package ch.glauser.gestionstock.identite.entity;

import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import ch.glauser.gestionstock.identite.model.Titre;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "PersonnePhysique")
public class PersonnePhysiqueEntity extends IdentiteEntity<PersonnePhysique> {
    @Enumerated(EnumType.STRING)
    @Column(name = "TITRE", nullable = false)
    private Titre titre;
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Column(name = "PRENOM", nullable = false)
    private String prenom;

    public PersonnePhysiqueEntity(PersonnePhysique personnePhysique) {
        super(personnePhysique);
        this.titre = personnePhysique.getTitre();
        this.nom = personnePhysique.getNom();
        this.prenom = personnePhysique.getPrenom();
    }

    @Override
    protected PersonnePhysique toDomainChild() {
        PersonnePhysique personnePhysique = new PersonnePhysique();
        personnePhysique.setTitre(this.titre);
        personnePhysique.setNom(this.nom);
        personnePhysique.setPrenom(this.prenom);
        return personnePhysique;
    }

    @Override
    protected String designation() {
        return this.prenom + " " + this.nom;
    }
}
