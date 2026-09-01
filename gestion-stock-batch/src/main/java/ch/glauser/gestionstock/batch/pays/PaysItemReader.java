package ch.glauser.gestionstock.batch.pays;

import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * Reader permettant de lire les pays depuis l'API externe fournit par <a href="https://restcountries.com">https://restcountries.com</a>
 *
 * @deprecated Cette API n'est désormais plus disponible en lecture sans clé API, le code ci-dessous n'est donc plus utilisable
 */
@Deprecated(forRemoval = true)
@Component("paysItemReader")
public class PaysItemReader implements ItemReader<Object> {

    public static final String FIELDS_PARAM_NAME = "fields";
    public static final List<String> FIELDS = List.of(
            PaysItemProcessor.MAP_PAYS_NAME,
            PaysItemProcessor.MAP_PAYS_CCA2,
            PaysItemProcessor.MAP_PAYS_TRANSLATION
    );

    private List<?> pays;

    @Override
    public Object read() {
        if (Objects.isNull(pays)) {
            final URI uri = UriComponentsBuilder
                    .newInstance()
                    .scheme("https")
                    .host("restcountries.com")
                    .pathSegment("v3.1", "all")
                    .queryParam(FIELDS_PARAM_NAME, FIELDS)
                    .build()
                    .toUri();

            this.pays = RestClient.create()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(List.class);
        }

        return CollectionUtils.isEmpty(pays) ? null : pays.removeFirst();
    }
}
