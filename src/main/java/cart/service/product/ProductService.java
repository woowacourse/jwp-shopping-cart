package cart.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.cart.CartDao;
import cart.dao.product.ProductDao;
import cart.domain.product.Product;
import cart.service.product.dto.ProductDto;
import cart.service.product.dto.SaveProductDto;
import cart.service.product.dto.UpdateProductDto;

@Service
@Transactional
public class ProductService {

	private static final int EXPECTED_ROW_COUNT = 1;

	private final ProductDao productDao;
	private final CartDao cartDao;

	public ProductService(final ProductDao productDao, final CartDao cartDao) {
		this.productDao = productDao;
		this.cartDao = cartDao;
	}

	@Transactional(readOnly = true)
	public List<ProductDto> findAll() {
		return productDao.findAll().stream()
			.map(this::mapProductToProductDto)
			.collect(Collectors.toList());
	}

	public ProductDto saveProducts(final SaveProductDto saveProductDto) {
		final Product product = new Product(saveProductDto);
		final long savedId = productDao.save(product);
		product.setId(savedId);

		return mapProductToProductDto(product);
	}

	public ProductDto updateProducts(final UpdateProductDto updateProductDto) {
		final Product product = new Product(updateProductDto);
		final int updatedRow = productDao.updateById(product);

		if (updatedRow != EXPECTED_ROW_COUNT) {
			throw new IllegalArgumentException("해당하는 상품이 없습니다.");
		}

		return mapProductToProductDto(product);
	}

	public void deleteProductsById(final Long id) {
		cartDao.deleteByProductId(id);
		final int deletedProductRow = productDao.deleteById(id);

		if (deletedProductRow != EXPECTED_ROW_COUNT) {
			throw new IllegalArgumentException("해당하는 상품이 없습니다.");
		}
	}

	private ProductDto mapProductToProductDto(final Product product) {
		return new ProductDto(product.getId(), product.getName(), product.getImage(), product.getPrice());
	}
}
