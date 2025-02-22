package ch.glauser.gestionstock.machine.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.contact.dto.ContactDto;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.gestionstock.piece.dto.PieceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

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
    private ContactDto contact;
    private List<PieceDto> pieces;

    public MachineDto(Machine machine) {
        super(machine);
        this.nom = machine.getNom();
        this.description = machine.getDescription();
        this.contact = Optional.ofNullable(machine.getContact()).map(ContactDto::new).orElse(null);
        this.pieces = CollectionUtils.emptyIfNull(machine.getPieces()).stream().map(PieceDto::new).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    protected Machine toDomainChild() {
        Machine machine = new Machine();
        machine.setNom(this.nom);
        machine.setDescription(this.description);
        machine.setContact(Optional.ofNullable(contact).map(ModelDto::toDomain).orElse(null));
        machine.setPieces(CollectionUtils.emptyIfNull(this.pieces).stream().map(ModelDto::toDomain).collect(Collectors.toCollection(LinkedList::new)));
        return machine;
    }
}
