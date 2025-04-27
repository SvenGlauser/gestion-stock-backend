package ch.glauser.gestionstock.technique.controller;

import ch.glauser.gestionstock.technique.service.importation.ImportPieceApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/technique", produces="application/json")
@RequiredArgsConstructor
public class TechniqueController {

    private final ImportPieceApplicationService importPieceApplicationService;

    @PostMapping(path = "/import/piece")
    public ResponseEntity<Void> search(@RequestParam("file") MultipartFile file) {
        this.importPieceApplicationService.importPieceFromCSV(file);

        return ResponseEntity.ok().build();
    }
}
