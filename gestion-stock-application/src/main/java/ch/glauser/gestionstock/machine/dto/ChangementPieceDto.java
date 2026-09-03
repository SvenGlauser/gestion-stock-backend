package ch.glauser.gestionstock.machine.dto;

import ch.glauser.gestionstock.common.dto.ModelDto;
import ch.glauser.gestionstock.machine.model.ChangementPiece;
import ch.glauser.gestionstock.piece.dto.PieceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ChangementPieceDto extends ModelDto<ChangementPiece> {
    private PieceDto piece;
    private Integer quantite;
    private String description;

    public ChangementPieceDto(ChangementPiece changementPiece) {
        super(changementPiece);
        this.piece = Optional.ofNullable(changementPiece.getPiece()).map(PieceDto::new).orElse(null);
        this.quantite = changementPiece.getQuantite();
        this.description = changementPiece.getDescription();
    }

    @Override
    protected ChangementPiece toDomainChild() {
        ChangementPiece changementPiece = new ChangementPiece();
        changementPiece.setPiece(Optional.ofNullable(this.piece).map(PieceDto::toDomain).orElse(null));
        changementPiece.setQuantite(this.quantite);
        changementPiece.setDescription(Optional.ofNullable(this.description).map(StringUtils::trimToNull).orElse(null));
        return changementPiece;
    }
}
