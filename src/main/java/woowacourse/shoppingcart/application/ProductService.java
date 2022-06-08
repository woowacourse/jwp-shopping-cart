package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.domain.ProductNotFoundException;
import woowacourse.shoppingcart.ui.dto.ProductRequest;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long addProduct(final ProductRequest request) {
        return productDao.save(
            new Product(request.getName(),
                request.getPrice(),
                request.getImageUrl(),
                request.getDescription())
        );
    }

    public ProductResponse findProductById(final Long productId) {
        return ProductResponse.from(
            productDao.findProductById(productId)
                .orElseThrow(ProductNotFoundException::new)
        );
    }

    public List<ProductResponse> findProducts() {
        return productDao.findProducts()
            .stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteProductById(final Long productId) {
        if (!productDao.delete(productId)) {
            throw new ProductNotFoundException();
        }
    }
}
