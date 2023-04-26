package cart.service;

import java.util.List;

import cart.controller.request.ProductCreateRequest;
import cart.controller.request.ProductUpdateRequest;
import cart.dto.ProductDto;

public interface ProductService {
	List<ProductDto> findAll();
	long save(ProductCreateRequest request);
	long deleteByProductId(long productId);
	ProductDto update(long productId, ProductUpdateRequest request);
}
