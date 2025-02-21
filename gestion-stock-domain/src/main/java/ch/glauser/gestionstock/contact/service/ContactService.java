package ch.glauser.gestionstock.contact.service;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.contact.model.Contact;

/**
 * Service métier de gestion des contacts
 */
public interface ContactService {
    /**
     * Récupère un contact
     *
     * @param id Id du contact à récupérer
     * @return Le contact ou null
     */
    Contact getContact(Long id);

    /**
     * Récupère les contacts
     *
     * @param searchRequest Paramètres de recherche
     * @return Une liste de contact paginée
     */
    SearchResult<Contact> searchContact(SearchRequest searchRequest);

    /**
     * Crée un contact
     *
     * @param contact Contact à créer
     * @return Le contact créé
     */
    Contact createContact(Contact contact);

    /**
     * Modifie un contact
     *
     * @param contact Contact à modifier avec les nouvelles valeurs
     * @return Le contact modifié
     */
    Contact modifyContact(Contact contact);

    /**
     * Supprime un contact
     *
     * @param id Id du contact à supprimer
     */
    void deleteContact(Long id);

    /**
     * Vérifie s'il existe un contact avec cette localité
     *
     * @param id Id de la localité
     * @return {@code true} s'il en existe un, sinon {@code false}
     */
    boolean existContactWithIdLocalite(Long id);
}
