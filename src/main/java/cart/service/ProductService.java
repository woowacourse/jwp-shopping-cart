package cart.service;

import java.util.List;

import cart.domain.product.ProductId;
import cart.service.request.ProductUpdateRequest;
import cart.service.response.ProductResponse;

public interface ProductService {
	ProductId save(final ProductUpdateRequest request);

	List<ProductResponse> findAll();

	ProductId deleteByProductId(final ProductId productId);

	ProductResponse update(final ProductId productId, final ProductUpdateRequest request);
}
