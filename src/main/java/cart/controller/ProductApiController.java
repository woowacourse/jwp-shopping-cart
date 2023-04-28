package cart.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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
	public ResponseEntity<ProductResponse> createProducts(@Valid @RequestBody final ProductRequest productRequest) {
		final ProductResponse productResponse = productService.saveProducts(productRequest);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(productResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProducts(@PathVariable final Long id,
		@Valid @RequestBody final ProductRequest productRequest) {
		final ProductResponse productResponse = productService.updateProducts(id, productRequest);

		return ResponseEntity.ok()
			.body(productResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProducts(@PathVariable final Long id) {
		productService.deleteProductsById(id);

		return ResponseEntity.noContent()
			.build();
	}

	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> handleBindException(final MethodArgumentNotValidException exception) {
		final FieldError fieldError = exception.getBindingResult()
			.getFieldError();
		final String exceptionMessage = Objects.requireNonNull(fieldError)
			.getDefaultMessage();

		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(exceptionMessage));
	}
}
