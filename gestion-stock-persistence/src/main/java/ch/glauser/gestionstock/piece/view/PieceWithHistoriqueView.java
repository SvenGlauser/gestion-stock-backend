package ch.glauser.gestionstock.piece.view;

import ch.glauser.gestionstock.categorie.entity.CategorieEntity;
import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.common.view.EntityView;
import ch.glauser.gestionstock.fournisseur.entity.FournisseurEntity;
import ch.glauser.gestionstock.piece.entity.PieceHistoriqueEntity;
import ch.glauser.gestionstock.piece.model.Piece;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
@Immutable
@Entity(name = "PieceWithHistoriqueView")
@Table(name = "PIECE")
public class PieceWithHistoriqueView extends EntityView<Piece> {
    @Column(name = "NUMERO_INVENTAIRE")
    private String numeroInventaire;

    @Column(name = "NOM")
    private String nom;
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "FOURNISSEUR_ID")
    private FournisseurEntity fournisseur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CATEGORIE_ID")
    private CategorieEntity categorie;

    @Column(name = "QUANITE")
    private Long quantite;
    @Column(name = "PRIX")
    private Double prix;

    @OneToMany
    @JoinColumn(name="PIECE_ID", insertable = false)
    private List<PieceHistoriqueEntity> historique;

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
