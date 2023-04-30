package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductInsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.ProductUpdateRequestDto;
import cart.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(ProductDao productDao) {
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
