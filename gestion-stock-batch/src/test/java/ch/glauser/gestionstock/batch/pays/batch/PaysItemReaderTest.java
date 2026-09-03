package ch.glauser.gestionstock.batch.pays.batch;

import ch.glauser.gestionstock.batch.pays.client.PaysClient;
import ch.glauser.gestionstock.batch.pays.model.PaysApiDto;
import ch.glauser.gestionstock.pays.model.Pays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PaysItemReaderTest {

    @InjectMocks
    private PaysItemReader paysItemReader;

    @Mock
    private PaysClient paysClient;

    @Test
    void read() {
        PaysApiDto suisse = new PaysApiDto();
        suisse.setName("Switzerland");
        suisse.setTranslations(new PaysApiDto.Translations());
        suisse.getTranslations().setFr("Suisse");

        PaysApiDto france = new PaysApiDto();
        france.setName("France");
        france.setTranslations(new PaysApiDto.Translations());
        france.getTranslations().setFr("France");
        france.setAlpha2Code("FR");

        Mockito.when(paysClient.getAll()).thenReturn(new LinkedList<>(List.of(suisse, france)));

        PaysApiDto suisseRead = paysItemReader.read();
        assertThat(suisseRead).isNotNull();
        assertThat(suisseRead.getName()).isEqualTo(suisse.getName());
        assertThat(suisseRead.getTranslations().getFr()).isEqualTo(suisse.getTranslations().getFr());
        assertThat(suisseRead.getAlpha2Code()).isEqualTo(suisse.getAlpha2Code());

        PaysApiDto franceRead = paysItemReader.read();
        assertThat(franceRead).isNotNull();
        assertThat(franceRead.getName()).isEqualTo(france.getName());
        assertThat(franceRead.getTranslations().getFr()).isEqualTo(france.getTranslations().getFr());
        assertThat(franceRead.getAlpha2Code()).isEqualTo(france.getAlpha2Code());

        PaysApiDto lastRead = paysItemReader.read();
        assertThat(lastRead).isNull();
    }
}