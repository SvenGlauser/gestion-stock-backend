package ch.glauser.gestionstock.identite.controller;

import ch.glauser.gestionstock.common.pagination.SearchRequest;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.identite.dto.IdentiteLightDto;
import ch.glauser.gestionstock.identite.service.IdentiteApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/identite", produces="application/json")
@RequiredArgsConstructor
public class IdentiteController {

    private final IdentiteApplicationService identiteApplicationService;

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<IdentiteLightDto>> search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(this.identiteApplicationService.search(searchRequest));
    }
}
