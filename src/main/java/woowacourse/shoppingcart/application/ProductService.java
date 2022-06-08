package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.product.ProductFindResponse;
import woowacourse.shoppingcart.dto.product.ProductRequest;
import woowacourse.shoppingcart.exception.NoSuchProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductFindResponse> findProducts() {
        return productDao.findSellingProducts().stream()
                .map(ProductFindResponse::new)
                .collect(Collectors.toList());
    }

    public Long addProduct(ProductRequest productRequest) {
        return productDao.save(productRequest.toProduct());
    }

    public ProductFindResponse findProductById(Long productId) {
        Product product = productDao.findProductById(productId)
                .orElseThrow(NoSuchProductException::new);
        ;
        return new ProductFindResponse(product);
    }

    public void deleteProductById(Long productId) {
        productDao.delete(productId);
    }
}
