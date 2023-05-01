package cart.service.product;

import cart.domain.product.ProductId;
import cart.service.request.ProductCreateRequest;
import cart.service.request.ProductUpdateRequest;
import cart.service.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductId save(final ProductCreateRequest request);

    ProductResponse update(final ProductId productId, final ProductUpdateRequest request);

    ProductId deleteByProductId(final ProductId productId);
}
