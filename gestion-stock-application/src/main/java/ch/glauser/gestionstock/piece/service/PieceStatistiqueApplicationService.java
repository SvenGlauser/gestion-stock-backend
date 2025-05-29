package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.piece.statistique.PieceStatistique;

import java.util.List;

/**
 * Service applicatif de gestion des statistiques de pièces
 */
public interface PieceStatistiqueApplicationService {
    /**
     * Récupérer les statistiques de pièces
     */
    List<PieceStatistique> getStatistiques();
}
