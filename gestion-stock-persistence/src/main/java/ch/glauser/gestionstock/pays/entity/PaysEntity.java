package ch.glauser.gestionstock.pays.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.pays.model.Pays;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PAYS")
public class PaysEntity extends ModelEntity<Pays> {
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Column(name = "ABREVIATION", nullable = false)
    private String abreviation;

    public PaysEntity(Pays pays) {
        super(pays);
        this.nom = pays.getNom();
        this.abreviation = pays.getAbreviation();
    }

    @Override
    protected Pays toDomainChild() {
        Pays pays = new Pays();
        pays.setNom(this.nom);
        pays.setAbreviation(this.abreviation);
        return pays;
    }
}
