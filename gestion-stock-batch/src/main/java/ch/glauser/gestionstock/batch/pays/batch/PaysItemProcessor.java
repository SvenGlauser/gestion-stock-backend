package ch.glauser.gestionstock.batch.pays.batch;

import ch.glauser.gestionstock.batch.pays.model.PaysApiDto;
import ch.glauser.gestionstock.pays.model.Pays;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component("paysItemProcessor")
public class PaysItemProcessor implements ItemProcessor<PaysApiDto, Pays> {

    private final List<String> nomsDejaTraites = new ArrayList<>();

    @Override
    public Pays process(@NonNull PaysApiDto item) {
        String nom = item.getName();
        String nomFrancais = Optional
                .ofNullable(item.getTranslations())
                .map(PaysApiDto.Translations::getFr)
                .orElse(null);

        if (Objects.nonNull(nomFrancais) && !this.nomsDejaTraites.contains(nomFrancais)) {
            nom = nomFrancais;
        }

        nomsDejaTraites.add(nom);

        Pays pays = new Pays();
        pays.setNom(nom);
        pays.setAbreviation(item.getAlpha2Code());

        return pays;
    }
}
