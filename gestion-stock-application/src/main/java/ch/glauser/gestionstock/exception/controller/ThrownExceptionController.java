package ch.glauser.gestionstock.exception.controller;

import ch.glauser.filters.automatic.AutomaticSearchQuery;
import ch.glauser.gestionstock.common.pagination.SearchResult;
import ch.glauser.gestionstock.exception.dto.ThrownExceptionDto;
import ch.glauser.gestionstock.exception.service.ThrownExceptionApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/exception", produces="application/json")
@RequiredArgsConstructor
public class ThrownExceptionController {

    private final ThrownExceptionApplicationService thrownExceptionApplicationService;

    @PutMapping(path = "/{id}/{status}")
    public ResponseEntity<Void> changeStatus(@PathVariable(name = "id") Long id,
                                          @PathVariable(name = "status") Boolean status) {

        this.thrownExceptionApplicationService.changeStatus(id, status);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/search")
    public ResponseEntity<SearchResult<ThrownExceptionDto>> search(@RequestBody AutomaticSearchQuery automaticSearchQuery) {
        return ResponseEntity.ok(this.thrownExceptionApplicationService.search(automaticSearchQuery));
    }
}
