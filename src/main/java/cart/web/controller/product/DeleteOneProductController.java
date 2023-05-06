package cart.web.controller.product;

import cart.domain.product.usecase.DeleteProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class DeleteOneProductController {
    private final DeleteProductUseCase deleteProductService;

    public DeleteOneProductController(final DeleteProductUseCase deleteProductService) {
        this.deleteProductService = deleteProductService;
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long deleteId) {
        deleteProductService.delete(deleteId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
