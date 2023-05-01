package cart.service;

import cart.service.request.ProductCreateRequest;
import cart.service.request.ProductUpdateRequest;
import cart.service.response.ProductResponse;

import java.util.List;

public interface ProductService {
	List<ProductResponse> findAll();
	long save(final ProductCreateRequest request);
	long deleteByProductId(final long productId);
	ProductResponse update(final long productId, final ProductUpdateRequest request);
}
