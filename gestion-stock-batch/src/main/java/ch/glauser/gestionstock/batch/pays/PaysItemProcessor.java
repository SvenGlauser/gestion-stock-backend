package ch.glauser.gestionstock.batch.pays;

import ch.glauser.gestionstock.pays.model.Pays;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("paysItemProcessor")
public class PaysItemProcessor implements ItemProcessor<Object, Pays> {
    @Override
    public Pays process(@NonNull Object item) {
        if (item instanceof Map<?,?> mapItem) {
            Pays pays = new Pays();
            pays.setNom(((Map<?, ?>) mapItem.get("name")).get("common").toString());
            pays.setAbreviation(mapItem.get("cca2").toString());

            return pays;
        }

        return null;
    }
}
