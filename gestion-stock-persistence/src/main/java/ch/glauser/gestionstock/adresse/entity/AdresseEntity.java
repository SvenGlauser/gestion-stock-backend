package ch.glauser.gestionstock.adresse.entity;

import ch.glauser.gestionstock.adresse.model.Adresse;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.localite.entity.LocaliteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AdresseEntity {
    @Column(name = "RUE")
    private String rue;
    @Column(name = "NUMERO")
    private String numero;
    @ManyToOne
    @JoinColumn(name="LOCALITE_ID")
    private LocaliteEntity localite;

    /**
     * Transform un model en entité
     *
     * @param adresse Model
     */
    public AdresseEntity(Adresse adresse) {
        this.rue = adresse.getRue();
        this.numero = adresse.getNumero();
        this.localite = Optional.ofNullable(adresse.getLocalite()).map(LocaliteEntity::new).orElse(null);
    }

    /**
     * Transforme l'entité dans le model correspondant
     *
     * @return Un model de type {@link Adresse}
     */
    public Adresse toDomain() {
        Adresse adresse = new Adresse();
        adresse.setRue(this.rue);
        adresse.setNumero(this.numero);
        adresse.setLocalite(Optional.ofNullable(this.localite).map(ModelEntity::toDomain).orElse(null));
        return adresse;
    }
}
