package ch.glauser.gestionstock.batch.pays;

import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.service.PaysService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("paysItemWriter")
@RequiredArgsConstructor
public class PaysItemWriter implements ItemWriter<Pays> {

    private final PaysService paysService;

    @Override
    public void write(Chunk<? extends Pays> chunk) {
        chunk.forEach(paysService::createPays);
    }
}
