package cart.repository;

import cart.controller.request.ProductCreateRequest;

public interface ProductRepository {
	long save(ProductCreateRequest request);
}
