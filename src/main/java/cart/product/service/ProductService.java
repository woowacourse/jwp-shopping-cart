package cart.product.service;

import cart.product.dao.ProductDao;
import cart.product.dto.ProductInsertRequestDto;
import cart.product.dto.ProductResponseDto;
import cart.product.dto.ProductUpdateRequestDto;
import cart.product.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductEntity addProduct(final ProductInsertRequestDto productInsertRequestDto) {
        final ProductEntity product = ProductMapper.toEntity(productInsertRequestDto);
        return productDao.insert(product);
    }

    public List<ProductResponseDto> getProducts() {
        final List<ProductEntity> products = productDao.selectAll();

        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public int updateProduct(final ProductUpdateRequestDto productUpdateRequestDto) {
        final ProductEntity product = ProductMapper.toEntity(productUpdateRequestDto);
        return productDao.update(product);
    }

    public int deleteProduct(final int productId) {
       return productDao.delete(productId);
    }

    public ProductEntity findProductById(final int id) {
        return productDao.findById(id);
    }
}
