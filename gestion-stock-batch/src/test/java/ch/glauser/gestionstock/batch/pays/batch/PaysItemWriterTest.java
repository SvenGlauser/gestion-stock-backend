package ch.glauser.gestionstock.batch.pays.batch;

import ch.glauser.gestionstock.batch.pays.client.PaysClient;
import ch.glauser.gestionstock.batch.pays.model.PaysApiDto;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.repository.PaysRepository;
import ch.glauser.gestionstock.pays.service.PaysService;
import ch.glauser.validation.common.Error;
import ch.glauser.validation.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.infrastructure.item.Chunk;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PaysItemWriterTest {

    @InjectMocks
    private PaysItemWriter paysItemWriter;

    @Mock
    private PaysService paysService;

    @Mock
    private PaysRepository paysRepository;

    @Test
    void write() {
        Pays suisse = new Pays();
        suisse.setNom("Suisse");
        suisse.setAbreviation("CH");

        Pays france = new Pays();
        france.setNom("France");
        france.setAbreviation("FR");

        Mockito.when(paysService.create(suisse)).thenReturn(suisse);
        Mockito.when(paysService.create(france)).thenReturn(france);

        paysItemWriter.write(Chunk.of(suisse, france));

        Mockito.verify(paysService, Mockito.times(1)).create(suisse);
        Mockito.verify(paysService, Mockito.times(1)).create(france);

        Mockito.when(paysService.create(suisse)).thenThrow(new ValidationException(List.of()));
        Mockito.when(paysRepository.existByAbreviation(suisse.getAbreviation())).thenReturn(true);
        Mockito.when(paysRepository.getByAbreviation(suisse.getAbreviation())).thenReturn(suisse);

        paysItemWriter.write(Chunk.of(suisse));

        Mockito.verify(paysService, Mockito.times(2)).create(suisse);
        Mockito.verify(paysRepository, Mockito.times(1)).existByAbreviation(suisse.getAbreviation());
        Mockito.verify(paysRepository, Mockito.times(1)).getByAbreviation(suisse.getAbreviation());
        Mockito.verify(paysService, Mockito.times(0)).modify(suisse);

        Pays suisseModified = new Pays();
        suisseModified.setNom("Switzerland");
        suisseModified.setAbreviation("CH");

        Mockito.when(paysService.create(suisseModified)).thenThrow(new ValidationException(List.of()));
        Mockito.when(paysRepository.existByAbreviation(suisseModified.getAbreviation())).thenReturn(true);
        Mockito.when(paysRepository.getByAbreviation(suisseModified.getAbreviation())).thenReturn(suisse);
        Mockito.when(paysService.modify(suisseModified)).thenReturn(suisseModified);

        paysItemWriter.write(Chunk.of(suisseModified));

        Mockito.verify(paysService, Mockito.times(3)).create(suisseModified);
        Mockito.verify(paysRepository, Mockito.times(2)).existByAbreviation(suisseModified.getAbreviation());
        Mockito.verify(paysRepository, Mockito.times(2)).getByAbreviation(suisseModified.getAbreviation());
        Mockito.verify(paysService, Mockito.times(1)).modify(suisseModified);
    }
}