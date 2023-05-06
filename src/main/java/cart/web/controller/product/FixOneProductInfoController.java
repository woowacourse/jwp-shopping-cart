package cart.web.controller.product;

import cart.domain.product.service.dto.ProductModificationDto;
import cart.domain.product.usecase.FixProductInfoUseCase;
import cart.web.dto.request.ProductModificationRequest;
import cart.web.dto.response.ProductModificationResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class FixOneProductInfoController {
    private final FixProductInfoUseCase fixProductInfoService;

    public FixOneProductInfoController(final FixProductInfoUseCase fixProductInfoService) {
        this.fixProductInfoService = fixProductInfoService;
    }

    @PutMapping
    public ResponseEntity<ProductModificationResponse> updateProduct(
            @RequestBody @Valid final ProductModificationRequest request) {
        final ProductModificationDto productModificationDto = new ProductModificationDto(
                request.getId(),
                request.getName(),
                request.getPrice(),
                request.getCategory(),
                request.getImageUrl()
        );

        fixProductInfoService.fixProductInfo(productModificationDto);

        return ResponseEntity
                .ok(new ProductModificationResponse(request));
    }
}
