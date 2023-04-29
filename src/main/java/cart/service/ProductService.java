package cart.service;

import cart.controller.dto.ProductDto;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> getProducts() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    public long save(final ProductDto productDto) {
        final Product product = convertToEntity(productDto);
        return productDao.insert(product);
    }

    @Transactional
    public void update(final Long id, final ProductDto productDto) {
        final Product product = convertToEntity(productDto);
        int updatedCount = productDao.updateById(product, id);
        if (updatedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_INVALID_UPDATE);
        }
    }

    @Transactional
    public void delete(final Long id) {
        int deletedCount = productDao.deleteById(id);
        if (deletedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_INVALID_DELETE);
        }
    }

    public ProductDto getById(final Long id) {
        return productDao.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new GlobalException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private Product convertToEntity(final ProductDto productDto) {
        return new Product(productDto.getName(), productDto.getImageUrl(), productDto.getPrice(), productDto.getCategory());
    }

    private ProductDto convertToDto(final Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getImageUrl(),
                product.getPrice(), product.getCategory());
    }
}
