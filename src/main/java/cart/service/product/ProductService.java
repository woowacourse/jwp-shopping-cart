package cart.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.service.product.dto.ProductDto;
import cart.service.product.dto.SaveProductDto;
import cart.service.product.dto.UpdateProductDto;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional(readOnly = true)
	public List<ProductDto> findAll() {

		return productRepository.findAll().stream()
			.map(this::mapProductToProductDto)
			.collect(Collectors.toList());
	}

	public ProductDto saveProducts(final SaveProductDto saveProductDto) {
		final Product product = new Product(saveProductDto);
		final Product savedProduct = productRepository.saveProducts(product);

		return mapProductToProductDto(savedProduct);
	}

	public ProductDto updateProducts(final UpdateProductDto updateProductDto) {
		productRepository.findById(updateProductDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));

		final Product product = new Product(updateProductDto);
		final Product updatedProduct = productRepository.updateProducts(product);

		return mapProductToProductDto(updatedProduct);
	}

	public void deleteProductsById(final Long id) {
		productRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));

		productRepository.deleteProductsById(id);
	}

	private ProductDto mapProductToProductDto(final Product product) {
		return new ProductDto(product.getId(), product.getName(), product.getImage(), product.getPrice());
	}
}
