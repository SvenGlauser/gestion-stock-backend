package ch.glauser.gestionstock.piece.service;

import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceHistorique;
import ch.glauser.gestionstock.piece.repository.PieceHistoriqueRepository;
import ch.glauser.gestionstock.piece.repository.PieceRepository;
import ch.glauser.gestionstock.piece.statistique.PieceStatistique;
import ch.glauser.utilities.exception.TechnicalException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service métier de gestion des statistiques de pièces
 */
@RequiredArgsConstructor
public class PieceStatistiqueServiceImpl implements PieceStatistiqueService {

    private final PieceRepository pieceRepository;
    private final PieceHistoriqueRepository pieceHistoriqueRepository;

    @Override
    public List<PieceStatistique> getStatistiques(List<FilterCombinator> filters) {
        final Map<LocalDate, PieceStatistique> mapStatistiques = new HashMap<>();

        final List<Piece> pieces = this.pieceRepository.searchAll(filters);

        for (Piece piece : pieces) {
            final List<PieceHistorique> piecesHistoriques = this.pieceHistoriqueRepository.findAllByIdPiece(piece.getId());

            for (PieceHistorique pieceHistorique : piecesHistoriques) {
                LocalDate date = pieceHistorique.getHeure().toLocalDate();

                mapStatistiques.putIfAbsent(date, new PieceStatistique(date));
                PieceStatistique pieceStatistique = mapStatistiques.get(date);

                pieceStatistique.addQuantite(pieceHistorique.getDifference(), piece.getPrix(), pieceHistorique.getType());
            }
        }

        List<PieceStatistique> statistiques = new ArrayList<>();

        for (PieceStatistique pieceStatistique : mapStatistiques.values()) {
            List<PieceStatistique> pieceStatistiquesBefore = mapStatistiques
                    .values()
                    .stream()
                    .filter(statistique -> statistique.getDate().isBefore(pieceStatistique.getDate()))
                    .collect(Collectors.toCollection(ArrayList::new));

            PieceStatistique statistiqueFinal = new PieceStatistique(pieceStatistique.getDate());
            long quantite = pieceStatistique.getQuantite() + pieceStatistiquesBefore
                            .stream()
                            .map(PieceStatistique::getQuantite)
                            .collect(Collectors.summarizingLong(Long::longValue))
                            .getSum();
            statistiqueFinal.setQuantite(quantite);
            double montantTotal = pieceStatistique.getMontantTotal() + pieceStatistiquesBefore
                            .stream()
                            .map(PieceStatistique::getMontantTotal)
                            .collect(Collectors.summarizingDouble(Double::doubleValue))
                            .getSum();
            statistiqueFinal.setMontantTotal(Math.round(montantTotal*100)/100D);

            statistiques.add(statistiqueFinal);
        }

        if (statistiques.isEmpty()) {
            return List.of();
        }

        LocalDate currentDate = statistiques
                .stream()
                .min(Comparator.comparing(PieceStatistique::getDate))
                .map(PieceStatistique::getDate)
                .map(date -> date.plusDays(1))
                .orElse(null);
        LocalDate maxDate = statistiques
                .stream()
                .max(Comparator.comparing(PieceStatistique::getDate))
                .map(PieceStatistique::getDate)
                .orElse(null);

        LocalDate now = LocalDate.now();

        if (Objects.isNull(maxDate) || maxDate.isBefore(now)) {
            maxDate = now;
        }

        while (Objects.nonNull(currentDate) && (currentDate.isBefore(maxDate) || currentDate.isEqual(maxDate))) {
            final LocalDate currentDateFinal = currentDate;
            if (statistiques.stream().noneMatch(statistique -> statistique.getDate().isEqual(currentDateFinal))) {
                PieceStatistique lastStatistique = statistiques
                        .stream()
                        .filter(statistique -> statistique.getDate().isEqual(currentDateFinal.minusDays(1)))
                        .findFirst().orElseThrow(() -> new TechnicalException("Erreur dans l'algorithme des statistiques de pièces"));

                PieceStatistique newStatistique = new PieceStatistique(currentDate);
                newStatistique.setQuantite(lastStatistique.getQuantite());
                newStatistique.setMontantTotal(lastStatistique.getMontantTotal());
                statistiques.add(newStatistique);
            }
            currentDate = currentDate.plusDays(1);
        }

        return statistiques
                .stream()
                .sorted(Comparator.comparing(PieceStatistique::getDate))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
