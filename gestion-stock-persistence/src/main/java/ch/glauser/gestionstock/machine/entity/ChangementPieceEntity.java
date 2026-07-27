package ch.glauser.gestionstock.machine.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.machine.model.ChangementPiece;
import ch.glauser.gestionstock.machine.model.Service;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ChangementPiece")
@Table(name = "CHANGEMENT_PIECE")
public class ChangementPieceEntity extends ModelEntity<ChangementPiece> {
    @ManyToOne(optional = false)
    @JoinColumn(name="PIECE_ID", nullable = false)
    private PieceEntity piece;

    @Column(name="QUANTITE", nullable = false)
    private Integer quantite;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    public ChangementPieceEntity(ChangementPiece changementPiece) {
        super(changementPiece);
        this.piece = Optional.ofNullable(changementPiece.getPiece()).map(PieceEntity::new).orElse(null);
        this.quantite = changementPiece.getQuantite();
        this.description = changementPiece.getDescription();
    }

    @Override
    protected ChangementPiece toDomainChild() {
        ChangementPiece changementPiece = new ChangementPiece();
        changementPiece.setPiece(Optional.ofNullable(this.piece).map(PieceEntity::toDomain).orElse(null));
        changementPiece.setQuantite(this.quantite);
        changementPiece.setDescription(this.description);
        return changementPiece;
    }
}
