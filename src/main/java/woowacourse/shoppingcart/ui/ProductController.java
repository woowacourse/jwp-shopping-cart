package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.ProductResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts() {
		return ResponseEntity.ok(productService.findProducts()
			.stream()
			.map(ProductResponse::from)
			.collect(Collectors.toList()));
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable final Long productId) {
		return ResponseEntity.ok(ProductResponse.from(productService.findProductById(productId)));
	}
}
