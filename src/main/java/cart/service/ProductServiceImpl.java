package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.product.ProductId;
import cart.service.request.ProductUpdateRequest;
import cart.service.response.ProductResponse;
import cart.domain.product.Product;
import cart.repository.ProductRepository;

@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	public ProductServiceImpl(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional
	@Override
	public ProductId save(final ProductUpdateRequest request) {
		return productRepository.insert(new Product(request.getName(), request.getPrice(), request.getImage()));
	}

	public List<ProductResponse> findAll() {
		return productRepository.findAll()
			.stream()
			.map(product -> new ProductResponse(product.getId().getId(), product.getName(), product.getPrice(),
				product.getImage()))
			.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public ProductId deleteByProductId(final ProductId productId) {
		final boolean isDelete = productRepository.deleteByProductId(productId);

		if (!isDelete) {
			throw new IllegalStateException("상품 삭제에 실패했습니다.");
		}

		return productId;
	}

	@Transactional
	@Override
	public ProductResponse update(final ProductId productId, final ProductUpdateRequest request) {
		final Product product = productRepository.findByProductId(productId);
		productRepository.updateByProductId(productId,
			new Product(request.getName(), request.getPrice(), request.getImage()));

		return new ProductResponse(product.getId().getId(), product.getName(), product.getPrice(),
			product.getImage());
	}
}
