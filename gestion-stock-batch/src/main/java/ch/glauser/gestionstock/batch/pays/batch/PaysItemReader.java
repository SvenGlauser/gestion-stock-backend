package ch.glauser.gestionstock.batch.pays.batch;

import ch.glauser.gestionstock.batch.pays.client.PaysClient;
import ch.glauser.gestionstock.batch.pays.model.PaysApiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * Reader permettant de lire les pays depuis l'API externe
 */
@Component("paysItemReader")
@RequiredArgsConstructor
public class PaysItemReader implements ItemReader<PaysApiDto> {

    private final PaysClient paysClient;

    private List<PaysApiDto> pays;

    @Override
    public PaysApiDto read() {
        if (Objects.isNull(pays)) {
            pays = paysClient.getAll();
        }

        return CollectionUtils.isEmpty(pays) ? null : pays.removeFirst();
    }
}
