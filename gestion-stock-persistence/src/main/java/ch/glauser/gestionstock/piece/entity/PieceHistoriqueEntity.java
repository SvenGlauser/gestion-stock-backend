package ch.glauser.gestionstock.piece.entity;

import ch.glauser.gestionstock.common.entity.ModelEntity;
import ch.glauser.gestionstock.piece.model.PieceHistorique;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueSource;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "PieceHistorique")
@Table(name = "PIECE_HISTORIQUE")
public class PieceHistoriqueEntity extends ModelEntity<PieceHistorique> {
    @ManyToOne
    @JoinColumn(name = "PIECE_ID", nullable = false, updatable = false)
    private PieceEntity piece;

    @Column(name = "DIFFERENCE", nullable = false, updatable = false)
    private Long difference;

    @Column(name = "DATE", nullable = false, updatable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, updatable = false)
    private PieceHistoriqueType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "SOURCE", nullable = false, updatable = false)
    private PieceHistoriqueSource source;

    public PieceHistoriqueEntity(PieceHistorique pieceHistorique) {
        super(pieceHistorique);

        this.piece = Optional.ofNullable(pieceHistorique.getPiece()).map(PieceEntity::new).orElse(null);
        this.difference = pieceHistorique.getDifference();
        this.date = pieceHistorique.getDate();
        this.type = pieceHistorique.getType();
        this.source = pieceHistorique.getSource();
    }

    @Override
    protected PieceHistorique toDomainChild() {
        PieceHistorique pieceHistorique = new PieceHistorique();
        pieceHistorique.setPiece(Optional.ofNullable(this.piece).map(PieceEntity::toDomain).orElse(null));
        pieceHistorique.setDifference(this.difference);
        pieceHistorique.setDate(this.date);
        pieceHistorique.setType(this.type);
        pieceHistorique.setSource(this.source);
        return pieceHistorique;
    }
}
