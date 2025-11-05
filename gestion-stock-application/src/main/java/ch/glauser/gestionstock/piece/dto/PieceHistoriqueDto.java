package ch.glauser.gestionstock.piece.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.piece.model.PieceHistorique;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueSource;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PieceHistoriqueDto extends ModelDto<PieceHistorique> {
    private PieceDto piece;

    private Long difference;

    private LocalDateTime heure;

    private String type;
    private String source;

    public PieceHistoriqueDto(PieceHistorique pieceHistorique) {
        super(pieceHistorique);

        this.piece = Optional.ofNullable(pieceHistorique.getPiece()).map(PieceDto::new).orElse(null);
        this.difference = pieceHistorique.getDifference();
        this.heure = pieceHistorique.getHeure();
        this.type = pieceHistorique.getType().name();
        this.source = pieceHistorique.getSource().name();
    }

    @Override
    protected PieceHistorique toDomainChild() {
        PieceHistorique pieceHistorique = new PieceHistorique();
        pieceHistorique.setPiece(Optional.ofNullable(this.piece).map(PieceDto::toDomain).orElse(null));
        pieceHistorique.setDifference(this.difference);
        pieceHistorique.setHeure(this.heure);
        pieceHistorique.setType(Optional.ofNullable(this.type).map(PieceHistoriqueType::valueOf).orElse(null));
        pieceHistorique.setSource(Optional.ofNullable(this.source).map(PieceHistoriqueSource::valueOf).orElse(null));
        return pieceHistorique;
    }
}
