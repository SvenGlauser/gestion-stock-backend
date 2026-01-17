package ch.glauser.gestionstock.piece.view;

import ch.glauser.gestionstock.common.view.EntityView;
import ch.glauser.gestionstock.piece.entity.PieceEntity;
import ch.glauser.gestionstock.piece.model.PieceHistorique;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueSource;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@NoArgsConstructor
@Immutable
@Entity(name = "PieceHistoriqueView")
@Table(name = "PIECE_HISTORIQUE")
public class PieceHistoriqueView extends EntityView<PieceHistorique> {
    @ManyToOne
    @JoinColumn(name = "PIECE_ID")
    private PieceEntity piece;

    @Column(name = "DIFFERENCE")
    private Long difference;

    @Column(name = "HEURE")
    private LocalDateTime heure;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private PieceHistoriqueType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "SOURCE")
    private PieceHistoriqueSource source;

    @Override
    protected PieceHistorique toDomainChild() {
        PieceHistorique pieceHistorique = new PieceHistorique();
        pieceHistorique.setPiece(Optional.ofNullable(this.piece).map(PieceEntity::toDomain).orElse(null));
        pieceHistorique.setDifference(this.difference);
        pieceHistorique.setHeure(this.heure);
        pieceHistorique.setType(this.type);
        pieceHistorique.setSource(this.source);
        return pieceHistorique;
    }
}
