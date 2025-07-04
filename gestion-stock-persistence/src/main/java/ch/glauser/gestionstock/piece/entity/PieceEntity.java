package ch.glauser.gestionstock.piece.entity;

import ch.glauser.gestionstock.categorie.entity.CategorieEntity;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.fournisseur.entity.FournisseurEntity;
import ch.glauser.gestionstock.piece.model.Piece;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Piece")
@Table(name = "PIECE")
public class PieceEntity extends ModelEntity<Piece> {
    @Column(name = "NUMERO_INVENTAIRE", nullable = false, unique = true)
    private String numeroInventaire;

    @Column(name = "NOM", nullable = false)
    private String nom;
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FOURNISSEUR_ID", nullable = false)
    private FournisseurEntity fournisseur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CATEGORIE_ID", nullable = false)
    private CategorieEntity categorie;

    @Column(name = "QUANITE", nullable = false)
    private Long quantite;
    @Column(name = "PRIX", nullable = false)
    private Double prix;

    public PieceEntity(Piece piece) {
        super(piece);
        this.numeroInventaire = piece.getNumeroInventaire();
        this.nom = piece.getNom();
        this.description = piece.getDescription();
        this.fournisseur = Optional.ofNullable(piece.getFournisseur()).map(FournisseurEntity::new).orElse(null);
        this.categorie = Optional.ofNullable(piece.getCategorie()).map(CategorieEntity::new).orElse(null);
        this.quantite = piece.getQuantite();
        this.prix = piece.getPrix();
    }

    @Override
    protected Piece toDomainChild() {
        Piece piece = new Piece();
        piece.setNumeroInventaire(this.numeroInventaire);
        piece.setNom(this.nom);
        piece.setDescription(this.description);
        piece.setFournisseur(Optional.ofNullable(this.fournisseur).map(ModelEntity::toDomain).orElse(null));
        piece.setCategorie(Optional.ofNullable(this.categorie).map(ModelEntity::toDomain).orElse(null));
        piece.setQuantite(this.quantite);
        piece.setPrix(this.prix);
        return piece;
    }
}
