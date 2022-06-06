package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsPerPageRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(ProductRequest productRequest) {
        return productDao.save(productRequest.toProduct());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts(ProductsPerPageRequest productsPerPageRequest) {
        Products products = productDao.findProducts();
        Products productsOfPage = products.getProductsOfPage(productsPerPageRequest.getSize(),
                productsPerPageRequest.getPage());
        return productsOfPage.getValue().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
