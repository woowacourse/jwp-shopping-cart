package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Price;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
@Transactional
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public ProductsResponse findProducts() {
        return new ProductsResponse(
                toProductResponses(productDao.findProducts()));
    }

    private List<ProductResponse> toProductResponses(List<Product> products) {
        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse addProduct(final ProductRequest productRequest) {
        final Product product = toProduct(productRequest);
        final Product savedProduct = productDao.save(product);
        return ProductResponse.of(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.findProductById(productId).orElseThrow(InvalidProductException::new);
        return ProductResponse.of(product);
    }

    public int deleteProductById(final long productId) {
        return productDao.delete(productId);
    }

    private Product toProduct(ProductRequest productRequest) {
        return new Product(
                productRequest.getName(),
                new Price(productRequest.getPrice()),
                productRequest.getImageUrl()
        );
    }
}
