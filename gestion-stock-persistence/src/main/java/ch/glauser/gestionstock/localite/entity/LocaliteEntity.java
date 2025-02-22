package ch.glauser.gestionstock.localite.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.pays.entity.PaysEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Localite")
@Table(
        name = "LOCALITE",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"NPA", "NOM", "PAYS_ID"})})
public class LocaliteEntity extends ModelEntity<Localite> {
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Column(name = "NPA", nullable = false)
    private String npa;
    @ManyToOne(optional = false)
    @JoinColumn(name="PAYS_ID", nullable = false)
    private PaysEntity pays;

    public LocaliteEntity(Localite localite) {
        super(localite);
        this.nom = localite.getNom();
        this.npa = localite.getNpa();
        this.pays = Optional.ofNullable(localite.getPays()).map(PaysEntity::new).orElse(null);
    }

    @Override
    protected Localite toDomainChild() {
        Localite localite = new Localite();
        localite.setNom(this.nom);
        localite.setNpa(this.npa);
        localite.setPays(Optional.ofNullable(this.pays).map(ModelEntity::toDomain).orElse(null));
        return localite;
    }
}
