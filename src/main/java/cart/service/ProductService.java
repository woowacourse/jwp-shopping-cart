package cart.service;

import java.util.List;

import cart.controller.request.ProductCreateRequest;
import cart.controller.request.ProductUpdateRequest;
import cart.controller.response.ProductResponse;

public interface ProductService {
	List<ProductResponse> findAll();
	long save(final ProductCreateRequest request);
	long deleteByProductId(final long productId);
	ProductResponse update(final long productId, final ProductUpdateRequest request);
}
