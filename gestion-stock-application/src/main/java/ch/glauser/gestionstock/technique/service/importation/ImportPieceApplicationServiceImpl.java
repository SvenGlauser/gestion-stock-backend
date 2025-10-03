package ch.glauser.gestionstock.technique.service.importation;

import ch.glauser.gestionstock.categorie.model.Categorie;
import ch.glauser.gestionstock.categorie.service.CategorieService;
import ch.glauser.gestionstock.common.pagination.Filter;
import ch.glauser.gestionstock.common.pagination.FilterCombinator;
import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.validation.common.Error;
import ch.glauser.gestionstock.validation.exception.TechnicalException;
import ch.glauser.gestionstock.validation.exception.ValidationException;
import ch.glauser.gestionstock.fournisseur.model.Fournisseur;
import ch.glauser.gestionstock.fournisseur.model.FournisseurConstantes;
import ch.glauser.gestionstock.fournisseur.service.FournisseurService;
import ch.glauser.gestionstock.piece.model.Piece;
import ch.glauser.gestionstock.piece.model.PieceConstantes;
import ch.glauser.gestionstock.piece.model.PieceHistoriqueSource;
import ch.glauser.gestionstock.piece.service.PieceService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implémentation du service d'importation des pièces
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImportPieceApplicationServiceImpl implements ImportPieceApplicationService {

    private static final String ID_COLUMN = "ID";
    private static final String NUMERO_COLUMN = "NUMERO";
    private static final String NOM_COLUMN = "NOM";
    private static final String QUANTITE_COLUMN = "QUANTITE";
    private static final String PRIX_UNITAIRE_COLUMN = "PRIX_UNITAIRE";
    private static final String PRIX_TOTAL_COLUMN = "PRIX_TOTAL";
    private static final String DESCRIPTION_COLUMN = "DESCRIPTION";
    private static final String QUANTITE_ANNEE_COLUMN = "QUANTITE_ANNEE";
    private static final String FOURNISSEUR_COLUMN = "NUMERO_FOURNISSEUR";

    private final PieceService pieceService;
    private final CategorieService categorieService;
    private final FournisseurService fournisseurService;

    @Override
    @Transactional
    public void importPieceFromCSV(MultipartFile file) {
        CSVParser parser;
        try {
            parser = CSVFormat.Builder
                    .create()
                    .setDelimiter(",")
                    .setHeader(
                            ID_COLUMN,
                            NUMERO_COLUMN,
                            NOM_COLUMN,
                            QUANTITE_COLUMN,
                            PRIX_UNITAIRE_COLUMN,
                            PRIX_TOTAL_COLUMN,
                            DESCRIPTION_COLUMN,
                            QUANTITE_ANNEE_COLUMN,
                            FOURNISSEUR_COLUMN
                    )
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(new BufferedReader(new InputStreamReader(file.getInputStream())));
        } catch (IOException e) {
            throw new TechnicalException("Impossible de lire le fichier");
        }

        Categorie categorie = this.getCategorie();

        Fournisseur fournisseurParDefaut = this.getDefaultFournisseur();

        List<ValidationException> errors = new ArrayList<>();

        for (CSVRecord csvRecord : parser) {
            try {
                this.createPiece(csvRecord, fournisseurParDefaut, categorie);
            } catch (ValidationException e) {
                String id = getString(csvRecord, ID_COLUMN);

                for (Error error : e.getErrors()) {
                    error.setMessage(error.getMessage() + ", id = " + id);
                }

                errors.add(e);
            }
        }

        if (CollectionUtils.isNotEmpty(errors)) {
            List<Error> errorsList = errors.stream()
                    .map(ValidationException::getErrors)
                    .flatMap(Collection::stream)
                    .toList();

            throw new ValidationException(errorsList);
        }

        categorie.setActif(false);
        this.categorieService.modify(categorie);
    }

    private void createPiece(CSVRecord csvRecord, Fournisseur fournisseurParDefaut, Categorie categorie) {
        String numero = getString(csvRecord, NUMERO_COLUMN);
        String nom = getString(csvRecord, NOM_COLUMN);
        String quantite = getString(csvRecord, QUANTITE_COLUMN);
        String prixUnitaire = getString(csvRecord, PRIX_UNITAIRE_COLUMN);
        String description = getString(csvRecord, DESCRIPTION_COLUMN);
        String quantiteAnnee = getString(csvRecord, QUANTITE_ANNEE_COLUMN);
        String fournisseurNom = getString(csvRecord, FOURNISSEUR_COLUMN);

        Piece piece = new Piece();
        piece.setNumeroInventaire(numero);
        piece.setNom(nom);
        piece.setDescription(description);
        piece.setFournisseur(this.getFournisseur(fournisseurNom, fournisseurParDefaut));
        piece.setCategorie(categorie);

        Long quantiteAnneeLong;
        try {
            quantiteAnneeLong = Long.parseLong(quantiteAnnee);
        } catch (NumberFormatException e) {
            quantiteAnneeLong = 0L;
        }

        try {
            piece.setPrix(Double.parseDouble(prixUnitaire));
        } catch (NumberFormatException e) {
            throw new ValidationException(new Error("Le prix n'est pas un nombre valide", PieceConstantes.FIELD_PRIX, Piece.class));
        }

        try {
            piece.setQuantite(Long.parseLong(quantite) + quantiteAnneeLong);
        } catch (NumberFormatException e) {
            throw new ValidationException(new Error("La quantité n'est pas un nombre valide", PieceConstantes.FIELD_QUANTITE, Piece.class));
        }

        piece = this.pieceService.create(piece);

        if (quantiteAnneeLong != 0) {
            piece.setQuantite(piece.getQuantite()-quantiteAnneeLong);
            this.pieceService.modify(piece, PieceHistoriqueSource.IMPORTATION);
        }
    }

    private Categorie getCategorie() {
        Categorie categorie = new Categorie();
        categorie.setNom("Pièces importées le " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        categorie.setDescription("Catégorie regroupant toutes les pièces importées depuis un fichier CSV");
        categorie.setActif(true);

        categorie = this.categorieService.create(categorie);
        return categorie;
    }

    private Fournisseur getFournisseur(String fournisseurNom, Fournisseur fournisseurParDefaut) {
        Fournisseur fournisseur;

        if (StringUtils.isNotBlank(fournisseurNom)) {
            SearchResult<Fournisseur> fournisseurs = this.searchFournisseurs(fournisseurNom);

            if (CollectionUtils.isNotEmpty(fournisseurs.getElements())) {
                fournisseur = fournisseurs.getElements().getFirst();
            } else {
                fournisseur = new Fournisseur();
                fournisseur.setNom(fournisseurNom);
                fournisseur = this.fournisseurService.create(fournisseur);
            }
        } else {
            fournisseur = fournisseurParDefaut;
        }
        return fournisseur;
    }

    private Fournisseur getDefaultFournisseur() {
        Fournisseur fournisseurParDefaut;

        SearchResult<Fournisseur> fournisseursIconnus = this.searchFournisseurs("Inconnu");

        if (CollectionUtils.isNotEmpty(fournisseursIconnus.getElements())) {
            fournisseurParDefaut = fournisseursIconnus.getElements().getFirst();
        } else {
            fournisseurParDefaut = new Fournisseur();
            fournisseurParDefaut.setNom("Inconnu");
            fournisseurParDefaut = this.fournisseurService.create(fournisseurParDefaut);
        }
        return fournisseurParDefaut;
    }

    private SearchResult<Fournisseur> searchFournisseurs(String nom) {
        Filter nameFilter = new Filter();
        nameFilter.setField(FournisseurConstantes.FIELD_NOM);
        nameFilter.setType(Filter.Type.EQUAL);
        nameFilter.setValue(nom);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setCombinators(List.of(FilterCombinator.and(List.of(nameFilter))));
        return this.fournisseurService.search(searchRequest);
    }

    private static String getString(CSVRecord csvRecord, String idColumn) {
        String value;
        try {
            value = csvRecord.get(idColumn);
        } catch (RuntimeException e) {
            value = "";
        }
        return StringUtils.trim(value);
    }
}
