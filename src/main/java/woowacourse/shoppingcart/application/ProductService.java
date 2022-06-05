package woowacourse.shoppingcart.application;

import java.util.stream.Collectors;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductResponses;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductRequest productRequest) {
        final Product product = convertRequestToProduct(productRequest);
        return productDao.save(product);
    }

    public ProductResponses findProducts() {
        final List<Product> products = productDao.findProducts();

        return new ProductResponses(products.stream()
                .map(this::convertResponseToProduct)
                .collect(Collectors.toList()));
    }

    public ProductResponse findProductById(final Long productId) {
        return convertResponseToProduct(productDao.findProductById(productId));
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }

    private Product convertRequestToProduct(ProductRequest productRequest) {
        return new Product(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl(),
                productRequest.getDescription(), productRequest.getStock());
    }

    private ProductResponse convertResponseToProduct(Product product) {
        return new ProductResponse(product.getName(), product.getPrice(), product.getImageUrl(),
                product.getDescription(), product.getStock());
    }
}
