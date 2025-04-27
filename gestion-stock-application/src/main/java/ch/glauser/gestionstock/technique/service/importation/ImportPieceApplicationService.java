package ch.glauser.gestionstock.technique.service.importation;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface utilisée pour importer des pièces depuis un fichier CSV
 */
public interface ImportPieceApplicationService {
    /**
     * Import les pièces
     * @param file Fichier CSV
     */
    void importPieceFromCSV(MultipartFile file);
}
