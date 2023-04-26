package cart.repository;

import java.util.List;

import cart.domain.Product;
import cart.controller.request.ProductCreateRequest;
import cart.controller.request.ProductUpdateRequest;

public interface ProductRepository {
	long save(ProductCreateRequest request);
	List<Product> findAll();
	long deleteByProductId(long productId);
	Product update(long productId, ProductUpdateRequest request);
}
