package ch.glauser.gestionstock.machine.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.machine.model.Service;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

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
    private Double duree;

    @Column(name = "DESCRIPTION_TRAVAUX")
    private String descriptionTravaux;

    @Column(name = "DIVERS")
    private String divers;

    public ServiceEntity(Service service) {
        super(service);
        this.machine = Optional.ofNullable(service.getMachine()).map(MachineEntity::new).orElse(null);
        this.date = service.getDate();
        this.duree = service.getDuree();
        this.descriptionTravaux = service.getDescriptionTravaux();
        this.divers = service.getDivers();
    }

    @Override
    protected Service toDomainChild() {
        Service service = new Service();
        service.setMachine(Optional.ofNullable(this.machine).map(MachineEntity::toDomain).orElse(null));
        service.setDate(this.date);
        service.setDuree(this.duree);
        service.setDescriptionTravaux(this.descriptionTravaux);
        service.setDivers(this.divers);
        return service;
    }
}
