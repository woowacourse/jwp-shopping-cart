package cart.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.product.ProductDao;
import cart.dao.product.dto.ProductCreateDTO;
import cart.dao.product.dto.ProductUpdateDTO;
import cart.domain.product.Product;
import cart.service.product.dto.ProductDto;
import cart.service.product.dto.SaveProductDto;
import cart.service.product.dto.UpdateProductDto;

@Service
@Transactional
public class ProductService {

	private final ProductDao productDao;

	public ProductService(final ProductDao productDao) {
		this.productDao = productDao;
	}

	@Transactional(readOnly = true)
	public List<ProductDto> findAll() {

		return productDao.findAll().stream()
			.map(this::mapProductToProductDto)
			.collect(Collectors.toList());
	}

	public ProductDto saveProducts(final SaveProductDto saveProductDto) {
		final ProductCreateDTO productCreateDTO = new ProductCreateDTO(saveProductDto.getName(),
			saveProductDto.getImage(), saveProductDto.getPrice());

		long savedId = productDao.save(productCreateDTO);

		return new ProductDto(savedId, saveProductDto.getName(),
			saveProductDto.getImage(), saveProductDto.getPrice());
	}

	public ProductDto updateProducts(final UpdateProductDto updateProductDto) {
		final ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO(updateProductDto.getId(),
			updateProductDto.getName(), updateProductDto.getImage(), updateProductDto.getPrice());

		long savedId = productDao.updateById(productUpdateDTO);

		return new ProductDto(savedId, productUpdateDTO.getName(),
			productUpdateDTO.getImage(), productUpdateDTO.getPrice());
	}

	public void deleteProductsById(final Long id) {
		productDao.deleteById(id);
	}

	private ProductDto mapProductToProductDto(final Product product) {
		return new ProductDto(product.getId(), product.getName(), product.getImage(), product.getPrice());
	}
}
