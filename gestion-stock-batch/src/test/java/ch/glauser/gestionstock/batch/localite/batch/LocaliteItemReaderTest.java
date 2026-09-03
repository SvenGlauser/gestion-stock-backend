package ch.glauser.gestionstock.batch.localite.batch;

import ch.glauser.gestionstock.batch.localite.client.LocaliteClient;
import ch.glauser.gestionstock.batch.localite.model.CantonApiDto;
import ch.glauser.gestionstock.batch.localite.model.LocaliteApiDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocaliteItemReaderTest {

    @InjectMocks
    private LocaliteItemReader localiteItemReader;

    @Mock
    private LocaliteClient localiteClient;

    @Test
    void read() {
        CantonApiDto cantonBern = new CantonApiDto();
        cantonBern.setKey(1);
        cantonBern.setName("Bern");

        CantonApiDto cantonZurich = new CantonApiDto();
        cantonZurich.setKey(2);
        cantonZurich.setName("Zurich");

        LocaliteApiDto bern = new LocaliteApiDto();
        bern.setCanton(cantonBern);
        bern.setName("Berne");
        bern.setPostalCode("2000");

        LocaliteApiDto sonceboz = new LocaliteApiDto();
        sonceboz.setCanton(cantonBern);
        sonceboz.setName("Sonceboz");
        sonceboz.setPostalCode("1999");

        List<LocaliteApiDto> zurich0a49 = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            LocaliteApiDto zurich = new LocaliteApiDto();
            zurich.setCanton(cantonZurich);
            zurich.setName("Zurich");
            zurich.setPostalCode(String.valueOf(2000+i));
            zurich0a49.add(zurich);
        }

        List<LocaliteApiDto> zurich50a99 = new ArrayList<>();
        for (int i = 50; i < 100; i++) {
            LocaliteApiDto zurich = new LocaliteApiDto();
            zurich.setCanton(cantonZurich);
            zurich.setName("Zurich");
            zurich.setPostalCode(String.valueOf(2000+i));
            zurich50a99.add(zurich);
        }

        List<LocaliteApiDto> zurich100 = new ArrayList<>();
        for (int i = 100; i < 101; i++) {
            LocaliteApiDto zurich = new LocaliteApiDto();
            zurich.setCanton(cantonZurich);
            zurich.setName("Zurich");
            zurich.setPostalCode(String.valueOf(2000+i));
            zurich100.add(zurich);
        }

        Mockito.when(localiteClient.getCantons()).thenReturn(List.of(cantonBern, cantonZurich));
        Mockito.when(localiteClient.getLocalites(1, 1, 50)).thenReturn(List.of(bern, sonceboz));
        Mockito.when(localiteClient.getLocalites(1, 2, 50)).thenReturn(null);
        Mockito.when(localiteClient.getLocalites(2, 1, 50)).thenReturn(zurich0a49);
        Mockito.when(localiteClient.getLocalites(2, 2, 50)).thenReturn(zurich50a99);
        Mockito.when(localiteClient.getLocalites(2, 3, 50)).thenReturn(zurich100);
        Mockito.when(localiteClient.getLocalites(2, 4, 50)).thenReturn(List.of());

        List<LocaliteApiDto> localites = new LinkedList<>();

        for (int i = 0; i < 103; i++) {
            localites.add(localiteItemReader.read());
        }

        assertThat(localiteItemReader.read()).isNull();

        List<LocaliteApiDto> localitesAll = new ArrayList<>();
        localitesAll.add(bern);
        localitesAll.add(sonceboz);
        localitesAll.addAll(zurich0a49);
        localitesAll.addAll(zurich50a99);
        localitesAll.addAll(zurich100);
        assertThat(localites).containsExactlyElementsOf(localitesAll);
    }
}