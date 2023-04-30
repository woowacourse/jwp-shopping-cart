package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.controller.request.ProductUpdateRequest;
import cart.controller.response.ProductResponse;
import cart.domain.Product;
import cart.repository.ProductRepository;

@Transactional(readOnly = true)
@Service
public class GeneralProductService implements ProductService {
	private final ProductRepository productRepository;

	public GeneralProductService(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<ProductResponse> findAll() {
		return productRepository.findAll()
			.stream()
			.map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(),
				product.getImage()))
			.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public long save(final ProductUpdateRequest request) {
		return productRepository.save(request);
	}

	@Transactional
	@Override
	public long deleteByProductId(final long productId) {
		final boolean isDelete = productRepository.deleteByProductId(productId) == productId;

		if (!isDelete) {
			throw new IllegalStateException("상품 삭제에 실패했습니다.");
		}

		return productId;
	}

	@Transactional
	@Override
	public ProductResponse update(final long productId, final ProductUpdateRequest request) {
		final long updateProductId = productRepository.updateByProductId(productId, request);
		final Product findProduct = productRepository.findByProductId(updateProductId)
			.orElseThrow(() -> new IllegalStateException("갱신된 상품 조회에 실패했습니다."));

		return new ProductResponse(findProduct.getId(), findProduct.getName(), findProduct.getPrice(),
			findProduct.getImage());
	}
}
