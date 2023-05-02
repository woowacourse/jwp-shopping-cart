package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.domain.ProductImage;
import cart.domain.ProductPrice;
import cart.domain.dto.ProductDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(@Qualifier("productJdbcDao") final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAll() {
        List<Product> products = productDao.findAll().stream()
                .map(productEntity -> new Product(
                        productEntity.getId(),
                        productEntity.getName(),
                        new ProductImage(productEntity.getImage()),
                        new ProductPrice(productEntity.getPrice()))
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
}
