package ch.glauser.gestionstock.machine.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.identite.dto.IdentiteDto;
import ch.glauser.gestionstock.identite.dto.IdentiteType;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.gestionstock.piece.dto.PieceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MachineDto extends ModelDto<Machine> {

    private String nom;
    private String description;
    private IdentiteDto proprietaire;
    private List<PieceDto> pieces;

    public MachineDto(Machine machine) {
        super(machine);
        this.nom = machine.getNom();
        this.description = machine.getDescription();
        this.proprietaire = Optional.ofNullable(machine.getProprietaire()).map(IdentiteType::castToDto).orElse(null);
        this.pieces = CollectionUtils.emptyIfNull(machine.getPieces()).stream().map(PieceDto::new).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    protected Machine toDomainChild() {
        Machine machine = new Machine();
        machine.setNom(Optional.ofNullable(this.nom).map(StringUtils::trimToNull).orElse(null));
        machine.setDescription(Optional.ofNullable(this.description).map(StringUtils::trimToNull).orElse(null));
        machine.setProprietaire(Optional.ofNullable(proprietaire).map(ModelDto::toDomain).orElse(null));
        machine.setPieces(CollectionUtils.emptyIfNull(this.pieces).stream().map(ModelDto::toDomain).collect(Collectors.toCollection(LinkedList::new)));
        return machine;
    }
}
