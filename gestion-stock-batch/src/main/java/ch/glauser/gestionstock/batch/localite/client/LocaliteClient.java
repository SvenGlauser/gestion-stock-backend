package ch.glauser.gestionstock.batch.localite.client;

import ch.glauser.gestionstock.batch.localite.model.CantonApiDto;
import ch.glauser.gestionstock.batch.localite.model.LocaliteApiDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Client en charge de faire la communication avec l'API des localités
 */
@FeignClient(value = "localite-client", url = "https://openplzapi.org/")
public interface LocaliteClient {
    /**
     * Récupère tous les cantons suisses
     * @return Une liste de cantons
     */
    @GetMapping(value = "/ch/Cantons", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CantonApiDto> getCantons();

    /**
     * Récupère toutes les localités du canton suisse
     *
     * @param idCanton Id du canton suisse
     * @param page Numéro de la page
     * @param pageSize Taille de la page
     *
     * @return Une liste de localité
     */
    @GetMapping(value = "/ch/Cantons/{idCanton}/Localities", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LocaliteApiDto> getLocalites(@PathVariable("idCanton") Integer idCanton,
                                      @RequestParam("page") Integer page,
                                      @RequestParam("pageSize") Integer pageSize);
}
