package cart.service;

import java.util.List;

import cart.dto.ProductDto;

public interface ProductService {
	List<ProductDto> findAll();
	long save(ProductCreateRequest request);
}
