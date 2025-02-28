package ch.glauser.gestionstock.batch.localite;

import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.localite.service.LocaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("localiteItemWriter")
@RequiredArgsConstructor
public class LocaliteItemWriter implements ItemWriter<Localite> {

    private final LocaliteService localiteService;

    @Override
    public void write(Chunk<? extends Localite> chunk) {
        chunk.forEach(localiteService::createLocalite);
    }
}
