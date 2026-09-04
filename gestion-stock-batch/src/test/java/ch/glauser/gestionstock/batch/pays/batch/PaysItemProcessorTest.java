package ch.glauser.gestionstock.batch.pays.batch;

import ch.glauser.gestionstock.batch.pays.model.PaysApiDto;
import ch.glauser.gestionstock.pays.model.Pays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PaysItemProcessorTest {

    @InjectMocks
    private PaysItemProcessor paysItemProcessor;

    @Test
    void process() {
        PaysApiDto suisse = new PaysApiDto();
        suisse.setName("Switzerland");
        suisse.setTranslations(new PaysApiDto.Translations());
        suisse.getTranslations().setFr("Suisse");

        PaysApiDto suisseDuplicate = new PaysApiDto();
        suisseDuplicate.setName("Switzerland");
        suisseDuplicate.setTranslations(new PaysApiDto.Translations());
        suisseDuplicate.getTranslations().setFr("Suisse");
        suisseDuplicate.setAlpha2Code("CH");

        PaysApiDto france = new PaysApiDto();
        france.setName("France");
        france.setTranslations(new PaysApiDto.Translations());
        france.getTranslations().setFr("France");
        france.setAlpha2Code("FR");

        Pays suisseProcessed = paysItemProcessor.process(suisse);
        assertThat(suisseProcessed).isNotNull();
        assertThat(suisseProcessed.getNom()).isEqualTo(suisse.getTranslations().getFr());
        assertThat(suisseProcessed.getAbreviation()).isEqualTo(suisse.getAlpha2Code());

        Pays suisseDuplicateProcessed = paysItemProcessor.process(suisseDuplicate);
        assertThat(suisseDuplicateProcessed).isNotNull();
        assertThat(suisseDuplicateProcessed.getNom()).isEqualTo(suisseDuplicate.getName());
        assertThat(suisseDuplicateProcessed.getAbreviation()).isEqualTo(suisseDuplicate.getAlpha2Code());

        Pays franceProcessed = paysItemProcessor.process(france);
        assertThat(franceProcessed).isNotNull();
        assertThat(franceProcessed.getNom()).isEqualTo(france.getTranslations().getFr());
        assertThat(franceProcessed.getAbreviation()).isEqualTo(france.getAlpha2Code());
    }
}