package ch.glauser.gestionstock.batch.localite;

import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import ch.glauser.gestionstock.localite.service.LocaliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("localiteItemWriter")
@RequiredArgsConstructor
public class LocaliteItemWriter implements ItemWriter<Localite> {

    private final LocaliteService localiteService;
    private final LocaliteRepository localiteRepository;

    @Override
    public void write(Chunk<? extends Localite> chunk) {
        chunk.forEach(localite -> {
            if (!this.localiteRepository.existByNpaAndNomAndIdPays(
                    localite.getNpa(),
                    localite.getNom(),
                    localite.getPays().getId()
            )) {
                localiteService.create(localite);
            }
        });
    }
}
