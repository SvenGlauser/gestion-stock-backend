package ch.glauser.gestionstock.localite.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.pays.dto.PaysDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class LocaliteDto extends ModelDto<Localite> {

    private String nom;
    private String npa;
    private PaysDto pays;

    public LocaliteDto(Localite localite) {
        super(localite);
        this.nom = localite.getNom();
        this.npa = localite.getNpa();
        this.pays = Optional.ofNullable(localite.getPays()).map(PaysDto::new).orElse(null);
    }

    @Override
    protected Localite toDomainChild() {
        Localite localite = new Localite();
        localite.setNom(Optional.ofNullable(this.nom).map(StringUtils::trimToNull).orElse(null));
        localite.setNpa(Optional.ofNullable(this.npa).map(StringUtils::trimToNull).orElse(null));
        localite.setPays(Optional.ofNullable(this.pays).map(ModelDto::toDomain).orElse(null));
        return localite;
    }
}
