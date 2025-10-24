package ch.glauser.gestionstock.fournisseur.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.gestionstock.identite.dto.IdentiteDto;
import ch.glauser.gestionstock.identite.dto.IdentiteType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class FournisseurDto extends ModelDto<Fournisseur> {

    private IdentiteDto identite;
    private String description;
    private String url;

    public FournisseurDto(Fournisseur fournisseur) {
        super(fournisseur);
        this.identite = Optional.ofNullable(fournisseur.getIdentite()).map(IdentiteType::castToDto).orElse(null);
        this.description = fournisseur.getDescription();
        this.url = fournisseur.getUrl();
    }

    @Override
    protected Fournisseur toDomainChild() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setIdentite(Optional.ofNullable(this.identite).map(ModelDto::toDomain).orElse(null));
        fournisseur.setDescription(Optional.ofNullable(this.description).map(StringUtils::trimToNull).orElse(null));
        fournisseur.setUrl(Optional.ofNullable(this.url).map(StringUtils::trimToNull).orElse(null));
        return fournisseur;
    }
}
