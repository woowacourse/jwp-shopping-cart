package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dto.ProductDto;
import cart.repository.ProductRepository;

@Transactional(readOnly = true)
@Service
public class GeneralProductService implements ProductService {
	private final ProductRepository productRepository;

	public GeneralProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<ProductDto> findAll() {
		return productRepository.findAll()
			.stream()
			.map(product -> new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImage()))
			.collect(Collectors.toList());
	}
}
