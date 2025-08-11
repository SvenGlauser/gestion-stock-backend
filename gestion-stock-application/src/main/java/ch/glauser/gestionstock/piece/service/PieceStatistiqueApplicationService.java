package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.piece.statistique.PieceStatistique;

import java.util.List;

/**
 * Service applicatif de gestion des statistiques de pièces
 */
public interface PieceStatistiqueApplicationService {
    /**
     * Récupérer les statistiques de pièces
     *
     * @param filters Filtres des pièces à prendre en compte pour les statistiques
     */
    List<PieceStatistique> getStatistiques(List<FilterCombinator> filters);
}
