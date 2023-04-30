package cart.service;

import static java.util.stream.Collectors.toList;

import cart.domain.Product;
import cart.dto.product.ProductDto;
import cart.entity.ProductEntity;
import cart.exception.ProductConstraintException;
import cart.exception.ProductNotFoundException;
import cart.repository.CartDao;
import cart.repository.ProductDao;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductDao productDao;
    private final CartDao cartDao;

    public ProductService(ProductDao productDao, CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    @Transactional
    public ProductDto createProduct(String name, int price, String imageUrl) {
        Product product = Product.Builder.builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .build();
        ProductEntity productEntity = productDao.save(product);
        return ProductDto.fromEntity(productEntity);
    }

    public List<ProductDto> findAllProducts() {
        return productDao.findAll().stream()
                .map(ProductDto::fromEntity)
                .collect(toList());
    }

    public void deleteById(Long id) {
        validateId(id);
        validateProductInCart(id);
        productDao.deleteById(id);
    }

    private void validateProductInCart(Long id) {
        if (cartDao.existsByProductId(id)) {
            throw new ProductConstraintException();
        }
    }

    @Transactional
    public ProductDto updateProductById(Long id, String name, int price, String imageUrl) {
        validateId(id);
        ProductEntity productEntity = ProductEntity.Builder.builder()
                .id(id)
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .build();
        productDao.update(productEntity);
        return ProductDto.fromEntity(productEntity);
    }

    private void validateId(Long id) {
        if (!productDao.existsById(id)) {
            throw new ProductNotFoundException("존재하지 않는 상품의 ID 입니다.");
        }
    }
}
