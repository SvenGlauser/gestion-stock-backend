package ch.glauser.gestionstock.machine.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.contact.entity.ContactEntity;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MACHINE")
public class MachineEntity extends ModelEntity<Machine> {
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CONTACT_ID", nullable = false)
    private ContactEntity contact;

    @OneToMany
    @JoinTable(
            name = "MACHINE_PIECE",
            joinColumns = @JoinColumn(name = "MACHINE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PIECE_ID"))
    private List<PieceEntity> pieces;

    public MachineEntity(Machine machine) {
        super(machine);
        this.nom = machine.getNom();
        this.description = machine.getDescription();
        this.contact = Optional.ofNullable(machine.getContact()).map(ContactEntity::new).orElse(null);
        this.pieces = CollectionUtils.emptyIfNull(machine.getPieces()).stream().map(PieceEntity::new).collect(Collectors.toList());
    }

    @Override
    protected Machine toDomainChild() {
        Machine machine = new Machine();
        machine.setNom(this.nom);
        machine.setDescription(this.description);
        machine.setContact(Optional.ofNullable(this.contact).map(ModelEntity::toDomain).orElse(null));
        machine.setPieces(CollectionUtils.emptyIfNull(this.pieces).stream().map(ModelEntity::toDomain).collect(Collectors.toList()));
        return machine;
    }
}
