package cart.repository.product;

import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.service.request.ProductUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    ProductId save(final Product product);

    List<Product> findAll();

    Optional<Product> findByProductId(final ProductId productId);

    ProductId updateByProductId(final ProductId productId, final ProductUpdateRequest request);

    ProductId deleteByProductId(final ProductId productId);
}
