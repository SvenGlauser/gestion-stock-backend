package ch.glauser.gestionstock.identite.dto;

import ch.glauser.gestionstock.adresse.dto.AdresseDto;
import ch.glauser.gestionstock.identite.model.Identite;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class IdentiteLightDto {

    private Long id;
    private String identiteType;
    private String designation;
    private String email;
    private String telephone;
    private AdresseDto adresse;
    private String remarques;

    public IdentiteLightDto(Identite identite) {
        this.id = identite.getId();
        this.identiteType = IdentiteType.getType(identite).name();
        this.designation = identite.designation();
        this.email = identite.getEmail();
        this.telephone = identite.getTelephone();
        this.adresse = Optional.ofNullable(identite.getAdresse()).map(AdresseDto::new).orElse(null);
        this.remarques = identite.getRemarques();
    }
}
