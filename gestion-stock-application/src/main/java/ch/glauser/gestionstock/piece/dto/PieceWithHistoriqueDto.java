package ch.glauser.gestionstock.piece.dto;

import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;
import ch.glauser.gestionstock.piece.pojo.PieceWithHistoriquePojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PieceWithHistoriqueDto {

    private Long id;

    private String numeroInventaire;

    private String nom;
    private String description;

    private FournisseurDto fournisseur;

    private CategorieDto categorie;

    private Long quantite;
    private Long quantiteEntree;
    private Long quantiteSortie;

    private Double prix;

    public PieceWithHistoriqueDto(PieceWithHistoriquePojo pieceWithHistoriquePojo) {
        this.id = pieceWithHistoriquePojo.getId();
        this.numeroInventaire = pieceWithHistoriquePojo.getNumeroInventaire();
        this.nom = pieceWithHistoriquePojo.getNom();
        this.description = pieceWithHistoriquePojo.getDescription();
        this.fournisseur = Optional.ofNullable(pieceWithHistoriquePojo.getFournisseur()).map(FournisseurDto::new).orElse(null);
        this.categorie = Optional.ofNullable(pieceWithHistoriquePojo.getCategorie()).map(CategorieDto::new).orElse(null);
        this.quantite = pieceWithHistoriquePojo.getQuantite();
        this.quantiteEntree = pieceWithHistoriquePojo.getQuantiteEntree();
        this.quantiteSortie = pieceWithHistoriquePojo.getQuantiteSortie();
        this.prix = pieceWithHistoriquePojo.getPrix();
    }
}
