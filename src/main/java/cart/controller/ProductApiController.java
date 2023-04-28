package cart.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cart.controller.dto.ExceptionResponse;
import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.service.product.ProductService;

@RestController
@RequestMapping("/products")
public class ProductApiController {

	private final ProductService productService;

	public ProductApiController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<ProductResponse> createProducts(@Valid @RequestBody ProductRequest productRequest) {
		ProductResponse productResponse = productService.saveProducts(productRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProducts(@PathVariable Long id,
		@Valid @RequestBody ProductRequest productRequest) {
		ProductResponse productResponse = productService.updateProducts(id, productRequest);

		return ResponseEntity.status(HttpStatus.OK).body(productResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProducts(@PathVariable Long id) {
		productService.deleteProductsById(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleBindException(MethodArgumentNotValidException exception) {
		final String exceptionMessage = exception.getBindingResult()
			.getFieldError()
			.getDefaultMessage();
		return ResponseEntity.badRequest().body(new ExceptionResponse(exceptionMessage));
	}
}
