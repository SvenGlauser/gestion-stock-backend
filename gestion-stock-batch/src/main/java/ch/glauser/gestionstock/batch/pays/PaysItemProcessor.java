package ch.glauser.gestionstock.batch.pays;

import ch.glauser.gestionstock.pays.model.Pays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("paysItemProcessor")
public class PaysItemProcessor implements ItemProcessor<Object, Pays> {

    public static final String MAP_PAYS_NAME = "name";
    public static final String MAP_PAYS_TRANSLATION = "translations";
    public static final String MAP_PAYS_FRA = "fra";
    public static final String MAP_PAYS_COMMON = "common";
    public static final String MAP_PAYS_CCA2 = "cca2";

    private List<String> nomsDejaTraites = new ArrayList<>();

    @Override
    public Pays process(@NonNull Object item) {
        if (item instanceof Map<?,?> mapItem) {
            Pays pays = new Pays();

            String nom = ((Map<?, ?>) mapItem.get(MAP_PAYS_NAME)).get(MAP_PAYS_COMMON).toString();
            Map<?, ?> mapTranslations = (Map<?, ?>) mapItem.get(MAP_PAYS_TRANSLATION);
            if (mapTranslations.containsKey(MAP_PAYS_FRA)) {
                String nomFra = ((Map<?, ?>) mapTranslations.get(MAP_PAYS_FRA)).get(MAP_PAYS_COMMON).toString();

                if (!nomsDejaTraites.contains(nomFra)) {
                    nom = nomFra;
                } else {
                    log.warn("Un pays avec le nom : {} est déjà existant, son nom officiel sera utilisé", nom);
                }
            }

            nomsDejaTraites.add(nom);

            pays.setNom(nom);
            pays.setAbreviation(mapItem.get(MAP_PAYS_CCA2).toString());

            return pays;
        }

        return null;
    }
}
