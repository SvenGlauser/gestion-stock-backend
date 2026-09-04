package ch.glauser.gestionstock.batch.pays.batch;

import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.repository.PaysRepository;
import ch.glauser.gestionstock.pays.service.PaysService;
import ch.glauser.validation.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Strings;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("paysItemWriter")
@Slf4j
@RequiredArgsConstructor
public class PaysItemWriter implements ItemWriter<Pays> {

    private final PaysService paysService;
    private final PaysRepository paysRepository;

    @Override
    public void write(Chunk<? extends Pays> chunk) {
        chunk.forEach(pays -> {
            try {
                this.paysService.create(pays);
            } catch (ValidationException e) {
                log.debug("Création du pays échouée avec l'erreur suivante", e);

                if (this.paysRepository.existByAbreviation(pays.getAbreviation())) {
                    Pays oldPays = this.paysRepository.getByAbreviation(pays.getAbreviation());

                    if (!Strings.CS.equals(oldPays.getNom(), pays.getNom())) {
                        oldPays.setNom(pays.getNom());
                        this.paysService.modify(oldPays);

                        log.info("Le pays [{}] a été modifié et son nom a été remplacé par [{}]", oldPays.getNom(), pays.getNom());
                    }
                }
            }
        });
    }
}
