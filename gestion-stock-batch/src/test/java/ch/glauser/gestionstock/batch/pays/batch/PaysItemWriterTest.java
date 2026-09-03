package ch.glauser.gestionstock.batch.pays.batch;

import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.repository.PaysRepository;
import ch.glauser.gestionstock.pays.service.PaysService;
import ch.glauser.validation.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.infrastructure.item.Chunk;

import java.util.List;

import static org.mockito.Mockito.*;

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

        when(paysService.create(suisse)).thenReturn(suisse);
        when(paysService.create(france)).thenReturn(france);

        paysItemWriter.write(Chunk.of(suisse, france));

        verify(paysService, times(1)).create(suisse);
        verify(paysService, times(1)).create(france);

        when(paysService.create(suisse)).thenThrow(new ValidationException(List.of()));
        when(paysRepository.existByAbreviation(suisse.getAbreviation())).thenReturn(true);
        when(paysRepository.getByAbreviation(suisse.getAbreviation())).thenReturn(suisse);

        paysItemWriter.write(Chunk.of(suisse));

        verify(paysService, times(2)).create(suisse);
        verify(paysRepository, times(1)).existByAbreviation(suisse.getAbreviation());
        verify(paysRepository, times(1)).getByAbreviation(suisse.getAbreviation());
        verify(paysService, times(0)).modify(suisse);

        Pays suisseModified = new Pays();
        suisseModified.setNom("Switzerland");
        suisseModified.setAbreviation("CH");

        when(paysService.create(suisseModified)).thenThrow(new ValidationException(List.of()));
        when(paysRepository.existByAbreviation(suisseModified.getAbreviation())).thenReturn(true);
        when(paysRepository.getByAbreviation(suisseModified.getAbreviation())).thenReturn(suisse);
        when(paysService.modify(suisseModified)).thenReturn(suisseModified);

        paysItemWriter.write(Chunk.of(suisseModified));

        verify(paysService, times(3)).create(suisseModified);
        verify(paysRepository, times(2)).existByAbreviation(suisseModified.getAbreviation());
        verify(paysRepository, times(2)).getByAbreviation(suisseModified.getAbreviation());
        verify(paysService, times(1)).modify(suisseModified);
    }
}