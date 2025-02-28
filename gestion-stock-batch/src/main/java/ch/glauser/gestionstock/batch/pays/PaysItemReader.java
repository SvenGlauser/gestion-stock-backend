package ch.glauser.gestionstock.batch.pays;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component("paysItemReader")
public class PaysItemReader implements ItemReader<Object> {
    private List<?> pays;

    @Override
    public Object read() {
        if (Objects.isNull(pays)) {
            RestTemplate restTemplate = new RestTemplate();

            this.pays = restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class);
        }

        return CollectionUtils.isEmpty(pays) ? null : pays.removeFirst();
    }
}
