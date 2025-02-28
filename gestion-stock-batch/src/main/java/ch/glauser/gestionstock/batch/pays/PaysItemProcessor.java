package ch.glauser.gestionstock.batch.pays;

import ch.glauser.gestionstock.pays.model.Pays;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("paysItemProcessor")
public class PaysItemProcessor implements ItemProcessor<Object, Pays> {

    public static final String MAP_PAYS_NAME = "name";
    public static final String MAP_PAYS_COMMON = "common";
    public static final String MAP_PAYS_CCA2 = "cca2";

    @Override
    public Pays process(@NonNull Object item) {
        if (item instanceof Map<?,?> mapItem) {
            Pays pays = new Pays();
            pays.setNom(((Map<?, ?>) mapItem.get(MAP_PAYS_NAME)).get(MAP_PAYS_COMMON).toString());
            pays.setAbreviation(mapItem.get(MAP_PAYS_CCA2).toString());

            return pays;
        }

        return null;
    }
}
