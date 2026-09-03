package ch.glauser.gestionstock.batch.localite.batch;

import ch.glauser.gestionstock.batch.localite.model.LocaliteApiDto;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import ch.glauser.gestionstock.localite.service.LocaliteService;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.service.PaysService;
import ch.glauser.utilities.exception.TechnicalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.infrastructure.item.Chunk;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class LocaliteItemWriterTest {

    @InjectMocks
    private LocaliteItemWriter localiteItemWriter;

    @Mock
    private LocaliteService localiteService;

    @Mock
    private LocaliteRepository localiteRepository;

    @Test
    void process() {
        Pays suisse = new Pays();
        suisse.setAbreviation("CH");
        suisse.setNom("Suisse");
        suisse.setId(1L);

        Localite localite = new Localite();
        localite.setNom("Bern");
        localite.setNpa("2000");
        localite.setPays(suisse);

        Mockito.when(localiteRepository.existByNpaAndNomAndIdPays(localite.getNpa(), localite.getNom(), suisse.getId())).thenReturn(true);

        localiteItemWriter.write(Chunk.of(localite));

        Mockito.verify(localiteService, Mockito.times(0)).create(localite);

        Mockito.when(localiteRepository.existByNpaAndNomAndIdPays(localite.getNpa(), localite.getNom(), suisse.getId())).thenReturn(false);

        localiteItemWriter.write(Chunk.of(localite));

        Mockito.verify(localiteService, Mockito.times(1)).create(localite);

        localiteItemWriter.write(Chunk.of(localite, localite));

        Mockito.verify(localiteService, Mockito.times(3)).create(localite);
    }
}