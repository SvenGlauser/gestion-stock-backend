package ch.glauser.gestionstock.pays.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.pays.model.Pays;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        pays.setNom(this.nom);
        pays.setAbreviation(this.abreviation);
        return pays;
    }
}
