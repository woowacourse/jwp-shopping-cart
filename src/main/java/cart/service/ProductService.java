package cart.service;

import java.util.List;

import cart.service.dto.ProductUpdateRequest;
import cart.controller.response.ProductResponse;

public interface ProductService {
	List<ProductResponse> findAll();
	long save(final ProductUpdateRequest request);
	long deleteByProductId(final long productId);
	ProductResponse update(final long productId, final ProductUpdateRequest request);
}
