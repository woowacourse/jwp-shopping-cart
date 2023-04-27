package cart.controller;

import cart.controller.request.ProductCreateRequest;
import cart.controller.request.ProductUpdateRequest;
import cart.controller.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class ProductApi {
	private final ProductService productService;

	public ProductApi(final ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductResponse>> getProducts() {
		final List<ProductResponse> products = productService.findAll();
		return ResponseEntity.ok(products);
	}

	@PostMapping("/products")
	public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductCreateRequest request) {
		final long save = productService.save(request);
		final URI uri = URI.create("/products/" + save);
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") long productId) {
		productService.deleteByProductId(productId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/products/{id}")
	public ResponseEntity<ProductResponse> updateProduct(
			@PathVariable(value = "id") long productId,
			@RequestBody @Valid ProductUpdateRequest request
	) {
		final ProductResponse productResponse = productService.update(productId, request);
		return ResponseEntity.ok(productResponse);
	}
}
