package cart.service;

import cart.dao.ProductDao;
import cart.dto.InsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.UpdateRequestDto;
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

    public ProductEntity addProduct(final InsertRequestDto insertRequestDto) {
        final ProductEntity product = ProductMapper.toEntity(insertRequestDto);
        return productDao.insert(product);
    }

    public List<ProductResponseDto> getProducts() {
        final List<ProductEntity> products = productDao.selectAll();

        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public int updateProduct(final UpdateRequestDto updateRequestDto) {
        final ProductEntity product = ProductMapper.toEntity(updateRequestDto);
        return productDao.update(product);
    }

    public int deleteProduct(final int productId) {
       return productDao.delete(productId);
    }

    public ProductEntity findProductById(final int id) {
        return productDao.findById(id);
    }
}
