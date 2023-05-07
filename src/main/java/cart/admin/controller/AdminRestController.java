package cart.admin.controller;

import cart.catalog.dto.ProductRequestDTO;
import cart.catalog.dto.ProductResponseDTO;
import cart.catalog.service.CatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final CatalogService catalogService;

    public AdminRestController(final CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> display() {
        final List<ProductResponseDTO> items = this.catalogService.display();
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @PostMapping("/products")
    public ResponseEntity<Void> create(@RequestBody final ProductRequestDTO productRequestDTO) {
        this.catalogService.create(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable final long id,
                                                     @RequestBody final ProductRequestDTO productRequestDTO) {
        final ProductResponseDTO updatedProduct = this.catalogService.update(id, productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        this.catalogService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
