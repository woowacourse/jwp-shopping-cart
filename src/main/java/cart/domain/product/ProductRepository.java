package cart.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

	List<Product> findAll();

	Product saveProducts(final Product product);

	Optional<Product> findById(Long id);

	Product updateProducts(final Product product);

	void deleteProductsById(final Long id);
}
