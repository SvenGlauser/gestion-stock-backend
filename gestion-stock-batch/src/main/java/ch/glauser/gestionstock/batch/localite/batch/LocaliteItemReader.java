package ch.glauser.gestionstock.batch.localite.batch;

import ch.glauser.gestionstock.batch.localite.client.LocaliteClient;
import ch.glauser.gestionstock.batch.localite.model.CantonApiDto;
import ch.glauser.gestionstock.batch.localite.model.LocaliteApiDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component("localiteItemReader")
@RequiredArgsConstructor
public class LocaliteItemReader implements ItemReader<LocaliteApiDto> {

    private final LocaliteClient localiteClient;

    private List<LocaliteApiDto> localites;

    @Override
    public LocaliteApiDto read() {
        if (Objects.isNull(this.localites)) {

            this.localites = new ArrayList<>();

            for (final CantonApiDto canton : this.localiteClient.getCantons()) {
                int page = 1;
                boolean lastPageEmpty = false;

                while (!lastPageEmpty) {
                    List<LocaliteApiDto> localites = this.localiteClient.getLocalites(
                            canton.getKey(),
                            page++,
                            50);

                    if (CollectionUtils.isNotEmpty(localites)) {
                        this.localites.addAll(localites);
                    } else {
                        lastPageEmpty = true;
                    }
                }
            }
        }

        return CollectionUtils.isEmpty(localites) ? null : localites.removeFirst();
    }
}
