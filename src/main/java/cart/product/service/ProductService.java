package cart.product.service;

import cart.product.domain.ProductId;
import cart.product.service.request.ProductCreateRequest;
import cart.product.service.request.ProductUpdateRequest;
import cart.product.service.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductId save(final ProductCreateRequest request);

    ProductResponse update(final ProductId productId, final ProductUpdateRequest request);

    ProductId deleteByProductId(final ProductId productId);
}
