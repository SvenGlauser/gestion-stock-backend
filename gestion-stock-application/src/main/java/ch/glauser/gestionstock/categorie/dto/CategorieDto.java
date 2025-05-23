package ch.glauser.gestionstock.categorie.dto;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.common.dto.ModelDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class CategorieDto extends ModelDto<Categorie> {

    private String nom;
    private String description;
    private Boolean actif;

    public CategorieDto(Categorie categorie) {
        super(categorie);
        this.nom = categorie.getNom();
        this.description = categorie.getDescription();
        this.actif = categorie.getActif();
    }

    @Override
    protected Categorie toDomainChild() {
        Categorie categorie = new Categorie();
        categorie.setNom(Optional.ofNullable(this.nom).map(StringUtils::trimToNull).orElse(null));
        categorie.setDescription(Optional.ofNullable(this.description).map(StringUtils::trimToNull).orElse(null));
        categorie.setActif(this.actif);
        return categorie;
    }
}
