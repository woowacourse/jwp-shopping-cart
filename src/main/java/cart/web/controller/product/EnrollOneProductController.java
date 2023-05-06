package cart.web.controller.product;

import cart.domain.product.service.dto.ProductCreationDto;
import cart.domain.product.usecase.EnrollOneProductUseCase;
import cart.web.dto.request.ProductCreationRequest;
import cart.web.dto.response.ProductCreationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class EnrollOneProductController {
    private final EnrollOneProductUseCase enrollProductService;

    public EnrollOneProductController(final EnrollOneProductUseCase enrollProductService) {
        this.enrollProductService = enrollProductService;
    }

    @PostMapping
    public ResponseEntity<ProductCreationResponse> createProduct(@RequestBody final ProductCreationRequest request) {
        final ProductCreationDto productCreationDto = toProductCreationDto(request);

        final Long savedProductId = enrollProductService.enroll(productCreationDto);

        final ProductCreationResponse productCreationResponse = new ProductCreationResponse(savedProductId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productCreationResponse);
    }

    private ProductCreationDto toProductCreationDto(final ProductCreationRequest request) {
        return new ProductCreationDto(request.getName(), request.getPrice(), request.getCategory(),
                request.getImageUrl());
    }
}
