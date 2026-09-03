package ch.glauser.gestionstock.batch.pays.client;

import ch.glauser.gestionstock.batch.pays.model.PaysApiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Client en charge de faire la communication avec l'API des pays
 */
@FeignClient(value = "pays-client", url = "https://countries.dev/")
public interface PaysClient {
    /**
     * Récupère tous les pays du monde
     * @return Une liste de pays
     */
    @GetMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_VALUE)
    List<PaysApiDto> getAll();
}
