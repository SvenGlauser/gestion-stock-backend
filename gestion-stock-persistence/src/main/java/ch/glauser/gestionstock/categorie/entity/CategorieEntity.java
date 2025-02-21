package ch.glauser.gestionstock.categorie.entity;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Categorie")
@Table(name = "CATEGORIE")
public class CategorieEntity extends ModelEntity<Categorie> {
    @Column(name = "NOM",
            nullable = false,
            unique = true)
    private String nom;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ACTIF")
    private boolean actif;

    public CategorieEntity(Categorie categorie) {
        super(categorie);
        this.nom = categorie.getNom();
        this.description = categorie.getDescription();
        this.actif = categorie.isActif();
    }

    @Override
    protected Categorie toDomainChild() {
        Categorie categorie = new Categorie();
        categorie.setNom(this.nom);
        categorie.setDescription(this.description);
        categorie.setActif(this.actif);
        return categorie;
    }
}
