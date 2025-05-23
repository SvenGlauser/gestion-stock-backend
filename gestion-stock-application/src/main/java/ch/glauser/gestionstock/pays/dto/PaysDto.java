package ch.glauser.gestionstock.pays.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.pays.model.Pays;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PaysDto extends ModelDto<Pays> {

    private String nom;
    private String abreviation;

    public PaysDto(Pays pays) {
        super(pays);
        this.nom = pays.getNom();
        this.abreviation = pays.getAbreviation();
    }

    @Override
    protected Pays toDomainChild() {
        Pays pays = new Pays();
        pays.setNom(Optional.ofNullable(this.nom).map(StringUtils::trimToNull).orElse(null));
        pays.setAbreviation(Optional.ofNullable(this.abreviation).map(StringUtils::trimToNull).orElse(null));
        return pays;
    }
}
