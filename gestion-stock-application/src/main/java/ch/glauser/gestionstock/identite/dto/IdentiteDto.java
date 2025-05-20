package ch.glauser.gestionstock.identite.dto;

import ch.glauser.gestionstock.adresse.dto.AdresseDto;
import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.identite.model.Identite;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "identiteType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PersonnePhysiqueDto.class, name = "PERSONNE_PHYSIQUE"),
        @JsonSubTypes.Type(value = PersonneMoraleDto.class, name = "PERSONNE_MORALE")
})
public abstract class IdentiteDto extends ModelDto<Identite> {

    private String identiteType;

    private String email;
    private String telephone;

    private AdresseDto adresse;

    private String remarques;

    protected IdentiteDto(Identite identite) {
        super(identite);
        this.identiteType = IdentiteType.getType(identite).name();
        this.email = identite.getEmail();
        this.telephone = identite.getTelephone();
        this.adresse = Optional.ofNullable(identite.getAdresse()).map(AdresseDto::new).orElse(null);
        this.remarques = identite.getRemarques();
    }

    @Override
    public Identite toDomain() {
        Identite identite = super.toDomain();
        identite.setEmail(this.email);
        identite.setTelephone(this.telephone);
        identite.setAdresse(Optional.ofNullable(this.adresse).map(AdresseDto::toDomain).orElse(null));
        identite.setRemarques(this.remarques);
        return identite;
    }
}
