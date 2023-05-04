package cart.repository.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import cart.dao.cart.CartDao;
import cart.dao.product.ProductDao;
import cart.dao.product.dto.ProductCreateDTO;
import cart.dao.product.dto.ProductUpdateDTO;
import cart.domain.product.Product;
import cart.domain.product.ProductRepository;

@Repository
public class ProductJdbcRepository implements ProductRepository {

	private final ProductDao productDao;
	private final CartDao cartDao;

	public ProductJdbcRepository(final ProductDao productDao, final CartDao cartDao) {
		this.productDao = productDao;
		this.cartDao = cartDao;
	}

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}

	@Override
	public Product saveProducts(final Product product) {
		final ProductCreateDTO productCreateDTO = new ProductCreateDTO(product);
		final long savedId = productDao.save(productCreateDTO);
		product.setId(savedId);

		return product;
	}

	@Override
	public Optional<Product> findById(final Long id) {
		return productDao.findById(id);
	}

	@Override
	public Product updateProducts(final Product product) {
		final ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO(product);
		productDao.updateById(productUpdateDTO);

		return product;
	}

	@Override
	public void deleteProductsById(final Long id) {
		cartDao.deleteByProductId(id);
		productDao.deleteById(id);
	}
}
