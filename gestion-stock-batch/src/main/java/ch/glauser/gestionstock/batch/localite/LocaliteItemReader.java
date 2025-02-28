package ch.glauser.gestionstock.batch.localite;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component("localiteItemReader")
public class LocaliteItemReader implements ItemReader<Object> {
    public static final String MAP_CANTON_KEY = "key";
    private List<Integer> cantonsIds;
    private List<Object> localites;

    @Override
    public Object read() {
        if (Objects.isNull(localites)) {
            RestTemplate restTemplate = new RestTemplate();

            this.localites = new ArrayList<>();

            this.getCantons(restTemplate);
            this.getLocalites(restTemplate);
        }

        return CollectionUtils.isEmpty(localites) ? null : localites.removeFirst();
    }

    private void getCantons(RestTemplate restTemplate) {
        List<?> cantons = restTemplate.getForObject("https://openplzapi.org/ch/Cantons", List.class);
        this.cantonsIds = new ArrayList<>();
        for (Object canton : CollectionUtils.emptyIfNull(cantons)) {
            if (canton instanceof Map<?, ?> cantonMap) {
                cantonsIds.add(Integer.parseInt(cantonMap.get(MAP_CANTON_KEY).toString()));
            }
        }
    }

    private void getLocalites(RestTemplate restTemplate) {
        for (Integer id : cantonsIds) {
            int page = 1;
            boolean continu = true;
            do {
                List<?> importedLocalites = restTemplate.getForObject("https://openplzapi.org/ch/Cantons/" + id + "/Localities?page=" + page + "&pageSize=50", List.class);
                if (CollectionUtils.isNotEmpty(importedLocalites)) {
                    this.localites.addAll(importedLocalites);
                    page++;
                } else {
                    continu = false;
                }
            } while (continu);
        }

    }
}
