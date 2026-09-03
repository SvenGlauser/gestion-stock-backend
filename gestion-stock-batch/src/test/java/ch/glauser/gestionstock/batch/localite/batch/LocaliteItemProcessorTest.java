package ch.glauser.gestionstock.batch.localite.batch;

import ch.glauser.gestionstock.batch.localite.model.LocaliteApiDto;
import ch.glauser.gestionstock.localite.model.Localite;
import ch.glauser.gestionstock.pays.model.Pays;
import ch.glauser.gestionstock.pays.service.PaysService;
import ch.glauser.utilities.exception.TechnicalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocaliteItemProcessorTest {

    @InjectMocks
    private LocaliteItemProcessor localiteItemProcessor;

    @Mock
    private PaysService paysService;

    @Test
    void process() {
        when(paysService.getByAbreviation("CH")).thenReturn(null);

        LocaliteApiDto localiteVide = new LocaliteApiDto();
        assertThatThrownBy(() -> localiteItemProcessor.process(localiteVide)).isInstanceOf(TechnicalException.class);

        Pays suisse = new Pays();
        suisse.setAbreviation("CH");
        suisse.setNom("Suisse");

        when(paysService.getByAbreviation("CH")).thenReturn(suisse);

        LocaliteApiDto localite = new LocaliteApiDto();
        localite.setName("Bern");
        localite.setPostalCode("2000");

        Localite localiteProcessed = localiteItemProcessor.process(localite);
        assertThat(localiteProcessed).isNotNull();
        assertThat(localiteProcessed.getNom()).isEqualTo(localite.getName());
        assertThat(localiteProcessed.getNpa()).isEqualTo(localite.getPostalCode());
    }
}