package cart.service.product;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.domain.dto.CartDto;
import cart.domain.dto.ProductDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductReadService {

    private final ProductDao productDao;

    public ProductReadService(@Qualifier("productJdbcDao") final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> getAll() {
        List<Product> products = productDao.findAll().stream()
                .map(productEntity -> new Product(
                        productEntity.getId(),
                        productEntity.getName(),
                        productEntity.getImage(),
                        productEntity.getPrice())
                )
                .collect(Collectors.toList());

        return products.stream()
                .map(this::fromProduct)
                .collect(Collectors.toList());
    }

    private ProductDto fromProduct(Product product) {
        return new ProductDto(product.getId(),
                product.getName(),
                product.getImage(),
                product.getPrice()
        );
    }

    public List<ProductDto> findById(final List<CartDto> cartDtos) {
        List<Product> products = cartDtos.stream()
                .map(cartDto -> productDao.findById(cartDto.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다.")))
                .map(Product::from)
                .collect(Collectors.toList());

        return products.stream()
                .map(this::fromProduct)
                .collect(Collectors.toList());
    }
}

