package ch.glauser.gestionstock.machine.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.machine.model.Service;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Service")
@Table(name = "SERVICE")
public class ServiceEntity extends ModelEntity<Service> {
    @ManyToOne(optional = false)
    @JoinColumn(name="MACHINE_ID", nullable = false)
    private MachineEntity machine;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "DUREE", nullable = false)
    private BigDecimal duree;

    @Column(name = "DESCRIPTION_TRAVAUX")
    private String descriptionTravaux;

    @Column(name = "DIVERS")
    private String divers;

    @OneToMany(
            targetEntity = ChangementPieceEntity.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "SERVICE_ID", nullable = false)
    private Set<ChangementPieceEntity> changementPieces;

    public ServiceEntity(Service service) {
        super(service);
        this.machine = Optional.ofNullable(service.getMachine()).map(MachineEntity::new).orElse(null);
        this.date = service.getDate();
        this.duree = service.getDuree();
        this.descriptionTravaux = service.getDescriptionTravaux();
        this.divers = service.getDivers();
        this.changementPieces = CollectionUtils
                .emptyIfNull(service.getChangementsPieces())
                .stream()
                .map(ChangementPieceEntity::new)
                .collect(Collectors.toSet());
    }

    @Override
    protected Service toDomainChild() {
        Service service = new Service();
        service.setMachine(Optional.ofNullable(this.machine).map(MachineEntity::toDomain).orElse(null));
        service.setDate(this.date);
        service.setDuree(this.duree);
        service.setDescriptionTravaux(this.descriptionTravaux);
        service.setDivers(this.divers);
        service.setChangementsPieces(CollectionUtils
                .emptyIfNull(this.changementPieces)
                .stream()
                .map(ChangementPieceEntity::toDomain)
                .collect(Collectors.toSet()));
        return service;
    }
}
