package ch.glauser.gestionstock.piece.view;

import ch.glauser.gestionstock.categorie.entity.CategorieEntity;
import ch.glauser.gestionstock.common.view.View;
import ch.glauser.gestionstock.fournisseur.entity.FournisseurEntity;
import ch.glauser.gestionstock.piece.entity.PieceHistoriqueEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.util.List;

@Getter
@NoArgsConstructor
@Immutable
@Entity(name = "PieceWithHistoriqueView")
@Table(name = "PIECE")
public class PieceWithHistoriqueView extends View {
    @Column(name = "NUMERO_INVENTAIRE", updatable = false, unique = true)
    private String numeroInventaire;

    @Column(name = "NOM", updatable = false , nullable = false)
    private String nom;
    @Column(name = "DESCRIPTION", updatable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FOURNISSEUR_ID", updatable = false, nullable = false)
    private FournisseurEntity fournisseur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CATEGORIE_ID", updatable = false, nullable = false)
    private CategorieEntity categorie;

    @Column(name = "QUANITE", updatable = false, nullable = false)
    private Long quantite;
    @Column(name = "PRIX", updatable = false, nullable = false)
    private Double prix;

    @OneToMany
    @JoinColumn(name="PIECE_ID", insertable = false, updatable = false, nullable = false)
    private List<PieceHistoriqueEntity> historique;
}
