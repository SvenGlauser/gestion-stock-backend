package ch.glauser.gestionstock.fournisseur.dto;

import ch.glauser.gestionstock.adresse.dto.AdresseDto;
import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class FournisseurDto extends ModelDto<Fournisseur> {

    private String nom;
    private String description;
    private String url;
    private AdresseDto adresse;

    public FournisseurDto(Fournisseur fournisseur) {
        super(fournisseur);
        this.nom = fournisseur.getNom();
        this.description = fournisseur.getDescription();
        this.url = fournisseur.getUrl();
        this.adresse = Optional.ofNullable(fournisseur.getAdresse()).map(AdresseDto::new).orElse(null);
    }

    @Override
    protected Fournisseur toDomainChild() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom(Optional.ofNullable(this.nom).map(StringUtils::trimToNull).orElse(null));
        fournisseur.setDescription(Optional.ofNullable(this.description).map(StringUtils::trimToNull).orElse(null));
        fournisseur.setUrl(Optional.ofNullable(this.url).map(StringUtils::trimToNull).orElse(null));
        fournisseur.setAdresse(Optional.ofNullable(this.adresse).map(AdresseDto::toDomain).orElse(null));
        return fournisseur;
    }
}
