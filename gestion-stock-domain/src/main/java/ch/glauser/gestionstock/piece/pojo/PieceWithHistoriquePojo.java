package ch.glauser.gestionstock.piece.pojo;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.gestionstock.piece.model.Piece;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PieceWithHistoriquePojo {

    private Long id;

    private String numeroInventaire;

    private String nom;
    private String description;

    private Fournisseur fournisseur;

    private Categorie categorie;

    private Long quantite;
    private Long quantiteEntreeAnneeCourante;
    private Long quantiteSortieAnneeCourante;

    private Double prix;

    public PieceWithHistoriquePojo(Piece piece,
                                   Long quantiteEntreeAnneeCourante,
                                   Long quantiteSortieAnneeCourante) {
        this.id = piece.getId();
        this.numeroInventaire = piece.getNumeroInventaire();
        this.nom = piece.getNom();
        this.description = piece.getDescription();
        this.fournisseur = piece.getFournisseur();
        this.categorie = piece.getCategorie();
        this.quantite = piece.getQuantite();
        this.quantiteEntreeAnneeCourante = quantiteEntreeAnneeCourante;
        this.quantiteSortieAnneeCourante = quantiteSortieAnneeCourante;
        this.prix = piece.getPrix();
    }
}
