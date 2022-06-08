package woowacourse.shoppingcart.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductDto;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Long addProduct(final ProductRequest productRequest) {
        Product product = new Product(
            productRequest.getName(),
            productRequest.getPrice(),
            productRequest.getStock(),
            productRequest.getImageURL()
        );

        return productDao.save(product);
    }

    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.findProductById(productId);
        ProductDto productDto = ProductDto.of(product);

        return new ProductResponse(productDto);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
