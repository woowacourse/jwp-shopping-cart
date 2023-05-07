package cart.web.controller.product;

import cart.domain.product.usecase.DeleteOneProductUseCase;
import cart.web.dto.request.PathVariableId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class DeleteOneProductController {
    private final DeleteOneProductUseCase deleteProductService;

    public DeleteOneProductController(final DeleteOneProductUseCase deleteProductService) {
        this.deleteProductService = deleteProductService;
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final PathVariableId deleteId) {
        deleteProductService.deleteSingleProductById(deleteId.getId());

        return ResponseEntity
                .noContent()
                .build();
    }
}
