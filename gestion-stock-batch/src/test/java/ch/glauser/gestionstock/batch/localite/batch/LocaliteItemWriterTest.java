package ch.glauser.gestionstock.batch.localite.batch;

import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.localite.repository.LocaliteRepository;
import ch.glauser.gestionstock.localite.service.LocaliteService;
import ch.glauser.gestionstock.pays.model.Pays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.infrastructure.item.Chunk;

import static org.mockito.Mockito.*;

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

        when(localiteRepository.existByNpaAndNomAndIdPays(localite.getNpa(), localite.getNom(), suisse.getId())).thenReturn(true);

        localiteItemWriter.write(Chunk.of(localite));

        verify(localiteService, times(0)).create(localite);

        when(localiteRepository.existByNpaAndNomAndIdPays(localite.getNpa(), localite.getNom(), suisse.getId())).thenReturn(false);

        localiteItemWriter.write(Chunk.of(localite));

        verify(localiteService, times(1)).create(localite);

        localiteItemWriter.write(Chunk.of(localite, localite));

        verify(localiteService, times(3)).create(localite);
    }
}