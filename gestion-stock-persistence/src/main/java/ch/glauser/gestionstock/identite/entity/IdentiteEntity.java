package ch.glauser.gestionstock.identite.entity;

import ch.glauser.gestionstock.adresse.entity.AdresseEntity;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.identite.model.Identite;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Identite")
@Table(name = "IDENTITE")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class IdentiteEntity<T extends Identite> extends ModelEntity<T> {

    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEPHONE")
    private String telephone;

    @Embedded
    private AdresseEntity adresse;

    @Column(name = "REMARQUES")
    private String remarques;

    // Champs calculé pour faire des recherches

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "DESIGNATION")
    private String designation;

    protected IdentiteEntity(T identite) {
        super(identite);
        this.email = identite.getEmail();
        this.telephone = identite.getTelephone();
        this.adresse = Optional.ofNullable(identite.getAdresse()).map(AdresseEntity::new).orElse(null);
        this.remarques = identite.getRemarques();
        this.designation = identite.designation();
    }

    @Override
    public T toDomain() {
        T identite = super.toDomain();
        identite.setEmail(this.email);
        identite.setTelephone(this.telephone);
        identite.setAdresse(Optional.ofNullable(this.adresse).map(AdresseEntity::toDomain).orElse(null));
        identite.setRemarques(this.remarques);
        return identite;
    }

    @PrePersist
    @PreUpdate
    public void updateCalculatedValue() {
        this.designation = this.designation();
    }

    /**
     * Récupère la désignation pour le champ calculé et persisté
     * @return La désignation de l'identité (prénom nom ou raison sociale, ....)
     */
    protected abstract String designation();
}
