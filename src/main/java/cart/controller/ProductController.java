package cart.controller;

import cart.controller.dto.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public final class ProductController {

	private final ProductService productService;

	public ProductController (final ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<ProductResponse>> requestProducts() {
		return ResponseEntity.ok(productService.findAll());
	}
}
