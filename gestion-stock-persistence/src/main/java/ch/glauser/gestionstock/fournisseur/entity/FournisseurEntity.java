package ch.glauser.gestionstock.fournisseur.entity;

import ch.glauser.gestionstock.adresse.entity.AdresseEntity;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Column(name = "DESCRIPTION")
    private String descripton;

    @Column(name = "URL")
    private String url;

    @Embedded
    private AdresseEntity adresse;

    public FournisseurEntity(Fournisseur fournisseur) {
        super(fournisseur);
        this.nom = fournisseur.getNom();
        this.descripton = fournisseur.getDescription();
        this.url = fournisseur.getUrl();
        this.adresse = Optional.ofNullable(fournisseur.getAdresse()).map(AdresseEntity::new).orElse(null);
    }

    @Override
    protected Fournisseur toDomainChild() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom(this.nom);
        fournisseur.setDescription(this.descripton);
        fournisseur.setUrl(this.url);
        fournisseur.setAdresse(Optional.ofNullable(this.adresse).map(AdresseEntity::toDomain).orElse(null));
        return fournisseur;
    }
}
