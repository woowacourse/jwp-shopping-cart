package cart.admin.controller;

import cart.catalog.dto.RequestProductDto;
import cart.catalog.dto.ResponseProductDto;
import cart.catalog.service.CatalogService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final CatalogService catalogService;
    
    public AdminController(final CatalogService catalogService) {
        this.catalogService = catalogService;
    }
    
    @GetMapping("/products")
    public ResponseEntity<List<ResponseProductDto>> display() {
        final List<ResponseProductDto> items = this.catalogService.display();
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
    
    @PostMapping("/products")
    public ResponseEntity<Void> create(@RequestBody final RequestProductDto requestProductDto) {
        this.catalogService.create(requestProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<ResponseProductDto> update(@PathVariable final long id,
            @RequestBody final RequestProductDto requestProductDto) {
        final ResponseProductDto updatedProduct = this.catalogService.update(id, requestProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);
    }
    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        this.catalogService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
