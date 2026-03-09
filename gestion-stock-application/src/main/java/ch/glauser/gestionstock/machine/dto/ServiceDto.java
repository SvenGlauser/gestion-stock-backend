package ch.glauser.gestionstock.machine.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.machine.model.Service;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ServiceDto extends ModelDto<Service> {
    private MachineDto machine;
    private LocalDate date;
    private Double duree;
    private String descriptionTravaux;
    private String divers;

    public ServiceDto(Service service) {
        super(service);
        this.machine = Optional.ofNullable(service.getMachine()).map(MachineDto::new).orElse(null);
        this.date = service.getDate();
        this.duree = service.getDuree();
        this.descriptionTravaux = service.getDescriptionTravaux();
        this.divers = service.getDivers();
    }

    @Override
    protected Service toDomainChild() {
        Service service = new Service();
        service.setMachine(Optional.ofNullable(this.machine).map(MachineDto::toDomain).orElse(null));
        service.setDate(this.date);
        service.setDuree(this.duree);
        service.setDescriptionTravaux(Optional.ofNullable(this.descriptionTravaux).map(StringUtils::trimToNull).orElse(null));
        service.setDivers(Optional.ofNullable(this.divers).map(StringUtils::trimToNull).orElse(null));
        return service;
    }
}
