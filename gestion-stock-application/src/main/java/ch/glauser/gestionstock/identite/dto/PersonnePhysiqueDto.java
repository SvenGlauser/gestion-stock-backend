package ch.glauser.gestionstock.identite.dto;

import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import ch.glauser.gestionstock.identite.model.Titre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PersonnePhysiqueDto extends IdentiteDto {

    private String titre;
    private String nom;
    private String prenom;

    public PersonnePhysiqueDto(PersonnePhysique personnePhysique) {
        super(personnePhysique);
        this.titre = Optional.ofNullable(personnePhysique.getTitre()).map(Titre::name).orElse(null);
        this.nom = personnePhysique.getNom();
        this.prenom = personnePhysique.getPrenom();
    }
    @Override
    protected PersonnePhysique toDomainChild() {
        PersonnePhysique personnePhysique = new PersonnePhysique();
        personnePhysique.setTitre(Optional.ofNullable(titre).map(Titre::valueOf).orElse(null));
        personnePhysique.setNom(Optional.ofNullable(this.nom).map(StringUtils::trimToNull).orElse(null));
        personnePhysique.setPrenom(Optional.ofNullable(this.prenom).map(StringUtils::trimToNull).orElse(null));
        return personnePhysique;
    }

    @Override
    public PersonnePhysique toDomain() {
        return (PersonnePhysique) super.toDomain();
    }
}
