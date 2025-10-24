package ch.glauser.gestionstock.fournisseur.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.gestionstock.identite.entity.IdentiteEntity;
import ch.glauser.gestionstock.identite.entity.PersonneMoraleEntity;
import ch.glauser.gestionstock.identite.entity.PersonnePhysiqueEntity;
import ch.glauser.gestionstock.identite.model.PersonneMorale;
import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import ch.glauser.utilities.exception.TechnicalException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Fournisseur")
@Table(name = "FOURNISSEUR")
public class FournisseurEntity extends ModelEntity<Fournisseur> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "IDENTITE_ID", nullable = false)
    private IdentiteEntity identite;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "URL")
    private String url;

    public FournisseurEntity(Fournisseur fournisseur) {
        super(fournisseur);
        this.identite = Optional
                .ofNullable(fournisseur.getIdentite())
                .map(identiteModel -> switch (identiteModel) {
                    case PersonnePhysique personnePhysique -> new PersonnePhysiqueEntity(personnePhysique);
                    case PersonneMorale personneMorale -> new PersonneMoraleEntity(personneMorale);
                    default -> throw new TechnicalException("Impossible de reconnaitre le type d'identité");
                })
                .orElse(null);
        this.description = fournisseur.getDescription();
        this.url = fournisseur.getUrl();
    }

    @Override
    protected Fournisseur toDomainChild() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setIdentite(Optional.ofNullable(this.identite).map(IdentiteEntity::toDomain).orElse(null));
        fournisseur.setDescription(this.description);
        fournisseur.setUrl(this.url);
        return fournisseur;
    }
}
