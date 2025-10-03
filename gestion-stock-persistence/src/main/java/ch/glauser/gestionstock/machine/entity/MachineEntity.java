package ch.glauser.gestionstock.machine.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.validation.exception.TechnicalException;
import ch.glauser.gestionstock.identite.entity.IdentiteEntity;
import ch.glauser.gestionstock.identite.entity.PersonneMoraleEntity;
import ch.glauser.gestionstock.identite.entity.PersonnePhysiqueEntity;
import ch.glauser.gestionstock.identite.model.PersonneMorale;
import ch.glauser.gestionstock.identite.model.PersonnePhysique;
import ch.glauser.gestionstock.machine.model.Machine;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import jakarta.persistence.*;
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
@Entity(name = "Machine")
@Table(
        name = "MACHINE",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"NOM", "PROPRIETAIRE_ID"})})
public class MachineEntity extends ModelEntity<Machine> {
    @Column(name = "NOM", nullable = false)
    private String nom;
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PROPRIETAIRE_ID", nullable = false)
    private IdentiteEntity proprietaire;

    @ManyToMany
    @JoinTable(
            name = "MACHINE_PIECE",
            joinColumns = @JoinColumn(name = "MACHINE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PIECE_ID"))
    private List<PieceEntity> pieces;

    public MachineEntity(Machine machine) {
        super(machine);
        this.nom = machine.getNom();
        this.description = machine.getDescription();
        this.proprietaire = Optional
                .ofNullable(machine.getProprietaire())
                .map(identite -> switch (identite) {
                        case PersonnePhysique personnePhysique -> new PersonnePhysiqueEntity(personnePhysique);
                        case PersonneMorale personneMorale -> new PersonneMoraleEntity(personneMorale);
                        default -> throw new TechnicalException("Impossible de reconnaitre le type d'identité");
                })
                .orElse(null);
        this.pieces = CollectionUtils.emptyIfNull(machine.getPieces()).stream().map(PieceEntity::new).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    protected Machine toDomainChild() {
        Machine machine = new Machine();
        machine.setNom(this.nom);
        machine.setDescription(this.description);
        machine.setProprietaire(Optional.ofNullable(this.proprietaire).map(ModelEntity::toDomain).orElse(null));
        machine.setPieces(CollectionUtils.emptyIfNull(this.pieces).stream().map(ModelEntity::toDomain).collect(Collectors.toCollection(LinkedList::new)));
        return machine;
    }
}
