package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.product.ProductRequest;
import woowacourse.shoppingcart.dto.product.ProductResponse;
import woowacourse.shoppingcart.exception.duplicateddata.ProductDuplicateException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
                productRequest.getImageUrl());
        validateNotDuplicateProduct(product);
        return productDao.save(product);
    }

    private void validateNotDuplicateProduct(Product product) {
        if (productDao.existProduct(product)) {
            throw new ProductDuplicateException("이미 존재하는 상품입니다.");
        }
    }

    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.findById(productId);
        return ProductResponse.from(product);
    }

    public List<ProductResponse> findProducts(final Long pageNumber, final Long limitCount) {
        List<Product> products = productDao.findProductsBy(pageNumber, limitCount);
        return convertToProductResponse(products);
    }

    private List<ProductResponse> convertToProductResponse(final List<Product> products) {
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }
}
