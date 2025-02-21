package ch.glauser.gestionstock.contact.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.contact.dto.ContactDto;

/**
 * Service applicatif de gestion des contacts
 */
public interface ContactApplicationService {
    /**
     * Récupère un contact
     *
     * @param id Id du contact à récupérer
     * @return Le contact ou null
     */
    ContactDto getContact(Long id);

    /**
     * Récupère les contacts
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de contact paginée
     */
    SearchResult<ContactDto> searchContact(SearchRequest searchRequest);

    /**
     * Crée un contact
     *
     * @param contact Contact à créer
     * @return Le contact créé
     */
    ContactDto createContact(ContactDto contact);

    /**
     * Modifie un contact
     *
     * @param contact Contact à modifier avec les nouvelles valeurs
     * @return Le contact modifié
     */
    ContactDto modifyContact(ContactDto contact);

    /**
     * Supprime un contact
     *
     * @param id Id du contact à supprimer
     */
    void deleteContact(Long id);
}
