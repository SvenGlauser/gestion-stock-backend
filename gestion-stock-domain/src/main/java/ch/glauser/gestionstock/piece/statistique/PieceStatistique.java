package ch.glauser.gestionstock.piece.statistique;

import ch.glauser.gestionstock.piece.model.PieceHistoriqueType;
import lombok.Data;

import java.time.LocalDate;

/**
 * Model représentant une statistique pour les pièces
 */
@Data
public class PieceStatistique {
    private LocalDate date;
    private Long quantite;
    private Double montantTotal;

    /**
     * Instancie une statistique pour un jour
     * @param date Date
     */
    public PieceStatistique(LocalDate date) {
        this.date = date;
        this.quantite = 0L;
        this.montantTotal = 0D;
    }

    /**
     * Ajoute des données à la statistique
     * @param quantite Quantité
     * @param prix Prix à l'unité
     * @param type Type d'historique
     */
    public void addQuantite(Long quantite, Double prix, PieceHistoriqueType type) {
        Long quantiteReelle = 0L;

        if (type == PieceHistoriqueType.ENTREE) {
            quantiteReelle = quantite;
        } else if (type == PieceHistoriqueType.SORTIE) {
            quantiteReelle = -quantite;
        }

        this.quantite += quantiteReelle;
        this.montantTotal += quantiteReelle*prix;
    }
}
