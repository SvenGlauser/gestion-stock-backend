package ch.glauser.gestionstock.adresse.dto;

import ch.glauser.gestionstock.adresse.model.Adresse;
import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.localite.dto.LocaliteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class AdresseDto {

    private String rue;
    private String numero;
    private LocaliteDto localite;

    /**
     * Transform un model en DTO
     *
     * @param adresse Model
     */
    public AdresseDto(Adresse adresse) {
        this.rue = adresse.getRue();
        this.numero = adresse.getNumero();
        this.localite = Optional.ofNullable(adresse.getLocalite()).map(LocaliteDto::new).orElse(null);
    }

    /**
     * Transforme la DTO dans le model correspondant
     *
     * @return Un model de type {@link Adresse}
     */
    public Adresse toDomain() {
        Adresse adresse = new Adresse();
        adresse.setRue(this.rue);
        adresse.setNumero(this.numero);
        adresse.setLocalite(Optional.ofNullable(this.localite).map(ModelDto::toDomain).orElse(null));
        return adresse;
    }
}
