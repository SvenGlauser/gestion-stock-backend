package ch.glauser.gestionstock.piece.dto;

import ch.glauser.gestionstock.categorie.dto.CategorieDto;
import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.fournisseur.dto.FournisseurDto;
import ch.glauser.gestionstock.piece.model.Piece;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PieceDto extends ModelDto<Piece> {

    private String numeroInventaire;

    private String nom;
    private String description;

    private FournisseurDto fournisseur;

    private CategorieDto categorie;

    private Long quantite;

    private Double prix;

    public PieceDto(Piece piece) {
        super(piece);
        this.numeroInventaire = piece.getNumeroInventaire();
        this.nom = piece.getNom();
        this.description = piece.getDescription();
        this.fournisseur = Optional.ofNullable(piece.getFournisseur()).map(FournisseurDto::new).orElse(null);
        this.categorie = Optional.ofNullable(piece.getCategorie()).map(CategorieDto::new).orElse(null);
        this.quantite = piece.getQuantite();
        this.prix = piece.getPrix();
    }

    @Override
    protected Piece toDomainChild() {
        Piece piece = new Piece();
        piece.setNumeroInventaire(Optional.ofNullable(this.numeroInventaire).map(StringUtils::trimToNull).orElse(null));
        piece.setNom(Optional.ofNullable(this.nom).map(StringUtils::trimToNull).orElse(null));
        piece.setDescription(Optional.ofNullable(this.description).map(StringUtils::trimToNull).orElse(null));
        piece.setFournisseur(Optional.ofNullable(this.fournisseur).map(ModelDto::toDomain).orElse(null));
        piece.setCategorie(Optional.ofNullable(this.categorie).map(ModelDto::toDomain).orElse(null));
        piece.setQuantite(this.quantite);
        piece.setPrix(this.prix);
        return piece;
    }
}
