package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.piece.statistique.PieceStatistique;

import java.util.List;

/**
 * Service métier de gestion des statistiques de pièces
 */
public interface PieceStatistiqueService {
    /**
     * Récupérer les statistiques de pièces
     */
    List<PieceStatistique> getStatistiques();
}
