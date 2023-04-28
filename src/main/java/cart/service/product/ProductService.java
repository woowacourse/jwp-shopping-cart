package cart.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.dao.ProductDao;
import cart.domain.product.Product;

@Service
@Transactional
public class ProductService {

	private final ProductDao productDao;

	public ProductService(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Transactional(readOnly = true)
	public List<ProductResponse> findAll() {
		return productDao.findAll().stream()
			.map(ProductResponse::new)
			.collect(Collectors.toList());
	}

	public ProductResponse saveProducts(ProductRequest productRequest) {
		Product product = new Product(null, productRequest.getName(), productRequest.getImage(),
			productRequest.getPrice(), null, null);
		long savedId = productDao.save(product);

		return new ProductResponse(savedId, product);
	}

	public ProductResponse updateProducts(Long id, ProductRequest productRequest) {
		Product product = new Product(null, productRequest.getName(), productRequest.getImage(),
			productRequest.getPrice(), null, null);
		long savedId = productDao.updateById(id, product);

		return new ProductResponse(savedId, product);
	}

	public void deleteProductsById(Long id) {
		productDao.deleteById(id);
	}
}
