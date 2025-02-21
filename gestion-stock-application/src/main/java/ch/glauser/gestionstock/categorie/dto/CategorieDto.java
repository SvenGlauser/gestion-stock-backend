package ch.glauser.gestionstock.categorie.dto;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.dto.ModelDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategorieDto extends ModelDto<Categorie> {

    private String nom;
    private String description;
    private boolean actif;

    public CategorieDto(Categorie categorie) {
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
