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
@Entity
@Table(name = "PIECE")
public class PieceEntity extends ModelEntity<Piece> {
    @Column(name = "NUMERO_INVENTAIRE", nullable = false)
    private String numeroInventaire;

    @Column(name = "NOM", nullable = false)
    private String nom;
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NUMERO_FOURNISSEUR")
    private String numeroFournisseur;

    @ManyToOne
    @JoinColumn(name = "FOURNISSEUR_ID")
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
        this.numeroFournisseur = piece.getNumeroFournisseur();
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
        piece.setNumeroFournisseur(this.numeroFournisseur);
        piece.setFournisseur(Optional.ofNullable(this.fournisseur).map(ModelEntity::toDomain).orElse(null));
        piece.setCategorie(Optional.ofNullable(this.categorie).map(ModelEntity::toDomain).orElse(null));
        piece.setQuantite(this.quantite);
        piece.setPrix(this.prix);
        return piece;
    }
}
