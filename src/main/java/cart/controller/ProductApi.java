package cart.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cart.controller.request.ProductCreateRequest;
import cart.controller.request.ProductUpdateRequest;
import cart.dto.ProductDto;
import cart.service.ProductService;

@RestController
public class ProductApi {
	private final ProductService productService;

	public ProductApi(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getProducts() {
		List<ProductDto> products = productService.findAll();
		return ResponseEntity.ok(products);
	}

	@PostMapping("/products")
	public ResponseEntity<Void> createProduct(@RequestBody ProductCreateRequest request) {
		long save = productService.save(request);
		URI uri = URI.create("/products/" + save);
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") long productId) {
		productService.deleteByProductId(productId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/products/{id}")
	public ResponseEntity<ProductDto> updateProduct(
		@PathVariable(value = "id") long productId,
		@RequestBody ProductUpdateRequest request
	) {
		ProductDto productDto = productService.update(productId, request);
		return ResponseEntity.ok(productDto);
	}
}
