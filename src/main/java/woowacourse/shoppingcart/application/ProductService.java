package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductRequest productRequest) {
        Product product =
                new Product(productRequest.getName(), productRequest.getPrice(), productRequest.getThumbnail());
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public ProductsResponse findProducts() {
        List<Product> products = productDao.findProducts();
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
        return new ProductsResponse(productResponses);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.findProductById(productId)
                .orElseThrow(() -> new InvalidProductException("존재하지 않는 상품입니다."));
        return ProductResponse.from(product);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
