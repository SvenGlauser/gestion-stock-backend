package ch.glauser.gestionstock.batch.localite;

import ch.glauser.gestionstock.common.validation.exception.TechnicalException;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.service.PaysService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component("localiteItemProcessor")
@RequiredArgsConstructor
public class LocaliteItemProcessor implements ItemProcessor<Object, Localite> {

    private final PaysService paysService;
    private Pays suisse;

    @Override
    public Localite process(@NonNull Object item) {
        if (Objects.isNull(this.suisse)) {
            this.suisse = this.paysService.getPaysByAbreviation("CH");

            if (Objects.isNull(this.suisse)) {
                throw new TechnicalException("Impossible de récupérer le pays : Suisse");
            }
        }

        if (item instanceof Map<?,?> mapItem) {
            Localite localite = new Localite();
            localite.setNom(mapItem.get("name").toString());
            localite.setNpa(mapItem.get("postalCode").toString());
            localite.setPays(this.suisse);

            return localite;
        }

        return null;
    }
}
