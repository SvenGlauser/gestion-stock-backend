package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.piece.statistique.PieceStatistique;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation du service applicatif de gestion des statistiques de pièces
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PieceStatistiqueApplicationServiceImpl implements PieceStatistiqueApplicationService {

    private final PieceStatistiqueService pieceStatistiqueService;

    @Override
    public List<PieceStatistique> getStatistiques() {
        return this.pieceStatistiqueService.getStatistiques();
    }
}
