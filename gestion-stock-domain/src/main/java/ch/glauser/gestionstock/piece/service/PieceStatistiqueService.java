package ch.glauser.gestionstock.piece.service;

import ch.glauser.filters.automatic.AutomaticSearchFieldCombinaison;
import ch.glauser.gestionstock.piece.statistique.PieceStatistique;

import java.util.List;

/**
 * Service métier de gestion des statistiques de pièces
 */
public interface PieceStatistiqueService {
    /**
     * Récupérer les statistiques de pièces
     *
     * @param filters Filtres des pièces à prendre en compte pour les statistiques
     */
    List<PieceStatistique> getStatistiques(List<AutomaticSearchFieldCombinaison> filters);
}
