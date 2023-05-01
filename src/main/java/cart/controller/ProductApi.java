package cart.controller;

import cart.service.ProductService;
import cart.service.request.ProductCreateRequest;
import cart.service.request.ProductUpdateRequest;
import cart.service.response.ProductResponse;
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
		final long save = productService.save(request);
		final URI uri = URI.create("/products/" + save);
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") long productId) {
		productService.deleteByProductId(productId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(
			@PathVariable(value = "id") long productId,
			@RequestBody @Valid ProductUpdateRequest request
	) {
		final ProductResponse productResponse = productService.update(productId, request);
		return ResponseEntity.ok(productResponse);
	}
}
