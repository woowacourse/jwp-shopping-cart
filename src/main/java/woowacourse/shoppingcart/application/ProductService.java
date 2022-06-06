package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductDto;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        final List<Product> products =  productDao.findProducts();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public Long addProduct(final ProductDto productDto) {
        final Product product = ProductDto.toProduct(productDto);
        return productDao.save(product);
    }

    public ProductResponse findProductById(final Long productId) {
        return ProductResponse.from(productDao.findProductById(productId));
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
