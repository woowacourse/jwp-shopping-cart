package cart.service;

import static java.util.stream.Collectors.toList;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import cart.exception.ProductNotFoundException;
import cart.repository.ProductDao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    public ProductDto createProduct(String name, int price, String imageUrl) {
        Product product = Product.builder()
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
        productDao.deleteById(id);
    }

    public ProductDto updateProductById(Long id, String name, int price, String imageUrl) {
        validateId(id);
        ProductEntity productEntity = ProductEntity.builder()
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
