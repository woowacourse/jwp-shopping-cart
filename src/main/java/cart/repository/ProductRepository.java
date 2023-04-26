package cart.repository;

import java.util.List;

import cart.domain.Product;
import cart.controller.request.ProductCreateRequest;
import cart.controller.request.ProductUpdateRequest;

public interface ProductRepository {
	long save(final ProductCreateRequest request);
	List<Product> findAll();
	long deleteByProductId(final long productId);
	Product update(final long productId, final ProductUpdateRequest request);
}
