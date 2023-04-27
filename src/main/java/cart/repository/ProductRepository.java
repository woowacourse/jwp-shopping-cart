package cart.repository;

import java.util.List;
import java.util.Optional;

import cart.domain.Product;
import cart.controller.request.ProductCreateRequest;
import cart.controller.request.ProductUpdateRequest;

public interface ProductRepository {
	long save(final ProductCreateRequest request);
	List<Product> findAll();
	Optional<Product> findByProductId(final long productId);
	long deleteByProductId(final long productId);
	long updateByProductId(final long productId, final ProductUpdateRequest request);
}
