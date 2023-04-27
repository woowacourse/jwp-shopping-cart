package cart.service;

import cart.controller.dto.ProductDto;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> getProducts() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductDto::fromEntity).collect(Collectors.toList());
    }

    public long save(final ProductDto productDto) {
        return productDao.insert(productDto.toEntity());
    }

    public void update(final Long id, final ProductDto productDto) {
        int updatedCount = productDao.update(productDto.toEntity(), id);
        if (updatedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_INVALID_UPDATE);
        }
    }

    public void delete(final Long id) {
        int deletedCount = productDao.deleteById(id);
        if (deletedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_INVALID_DELETE);
        }
    }

    public ProductDto getById(final Long id) {
        final Optional<Product> product = productDao.findById(id);
        if (product.isEmpty()) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        final Product findProduct = product.get();
        return ProductDto.fromEntity(findProduct);
    }
}
