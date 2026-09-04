package ch.glauser.gestionstock.batch.localite.batch;

import ch.glauser.gestionstock.batch.localite.model.LocaliteApiDto;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.service.PaysService;
import ch.glauser.utilities.exception.TechnicalException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("localiteItemProcessor")
@RequiredArgsConstructor
public class LocaliteItemProcessor implements ItemProcessor<LocaliteApiDto, Localite> {

    private static final String ABREVIATION_SUISSE = "CH";
    private static final String ERROR_IMPORT_LOCALITE_PAYS_SUISSE_INTROUVABLE = "Impossible de récupérer le pays : Suisse";

    private final PaysService paysService;

    private Pays suisse;

    @Override
    public Localite process(@NonNull LocaliteApiDto item) {
        if (Objects.isNull(this.suisse)) {
            this.suisse = this.paysService.getByAbreviation(ABREVIATION_SUISSE);

            if (Objects.isNull(this.suisse)) {
                throw new TechnicalException(ERROR_IMPORT_LOCALITE_PAYS_SUISSE_INTROUVABLE);
            }
        }

        Localite localite = new Localite();
        localite.setNom(item.getName());
        localite.setNpa(item.getPostalCode());
        localite.setPays(this.suisse);

        return localite;
    }
}
