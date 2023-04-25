package cart.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.product.dao.ProductDao;
import cart.product.domain.Product;
import cart.product.dto.ProductRequest;
import cart.product.dto.ProductResponse;

@Service
public class ProductService {

	private final ProductDao productDao;

	public ProductService(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Transactional
	public List<ProductResponse> findAll() {
		return productDao.findAll().stream()
			.map(ProductResponse::new)
			.collect(Collectors.toList());
	}

	public long saveProducts(ProductRequest productRequest) {
		Product product = new Product(null, productRequest.getName(), productRequest.getImage(),
			productRequest.getPrice(), null, null);
		return productDao.save(product);
	}

	public long updateProducts(Long id, ProductRequest productRequest) {
		Product product = new Product(null, productRequest.getName(), productRequest.getImage(),
			productRequest.getPrice(), null, null);
		return productDao.updateById(id, product);
	}

	public void deleteProductsById(Long id) {
		productDao.deleteById(id);
	}
}
