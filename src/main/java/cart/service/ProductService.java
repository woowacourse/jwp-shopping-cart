package cart.service;

import java.util.List;

import cart.controller.request.ProductCreateRequest;
import cart.controller.request.ProductUpdateRequest;
import cart.dto.ProductDto;

public interface ProductService {
	List<ProductDto> findAll();
	long save(final ProductCreateRequest request);
	long deleteByProductId(final long productId);
	ProductDto update(final long productId, final ProductUpdateRequest request);
}
