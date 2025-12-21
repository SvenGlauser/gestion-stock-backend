package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.piece.statistique.PieceStatistique;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole(T(ch.glauser.gestionstock.security.SecurityRoles).PIECE_STATISTIQUE_LECTEUR.name())")
    public List<PieceStatistique> getStatistiques(List<FilterCombinator> filters) {
        return this.pieceStatistiqueService.getStatistiques(filters);
    }
}
