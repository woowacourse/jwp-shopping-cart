package cart.product.api;

import cart.product.domain.ProductId;
import cart.product.service.ProductService;
import cart.product.service.request.ProductCreateRequest;
import cart.product.service.request.ProductUpdateRequest;
import cart.product.service.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductApi {
	private final ProductService productService;

	public ProductApi(final ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts() {
		final List<ProductResponse> products = productService.findAll();
		return ResponseEntity.ok(products);
	}

	@PostMapping
	public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductCreateRequest request) {
		final ProductId saveProductId = productService.save(request);
		final URI uri = URI.create("/products/" + saveProductId.getId());
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") long productId) {
		productService.deleteByProductId(ProductId.from(productId));
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(
			@PathVariable(value = "id") long productId,
			@RequestBody @Valid ProductUpdateRequest request
	) {
		final ProductResponse productResponse = productService.update(ProductId.from(productId), request);
		return ResponseEntity.ok(productResponse);
	}
}
