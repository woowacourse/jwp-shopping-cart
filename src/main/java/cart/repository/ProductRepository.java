package cart.repository;

import java.util.List;

import cart.domain.Product;
import cart.controller.request.ProductCreateRequest;

public interface ProductRepository {
	long save(ProductCreateRequest request);
	List<Product> findAll();
	long deleteByProductId(long productId);
}
