package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.PageServiceRequest;
import woowacourse.shoppingcart.application.dto.ProductSaveServiceRequest;
import woowacourse.shoppingcart.application.dto.ProductServiceResponse;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Page;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.DataNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long addProduct(final ProductSaveServiceRequest request) {
        return productDao.save(request.toEntity());
    }

    public ProductServiceResponse findById(final Long productId) {
        final Product product = findProduct(productId);
        return ProductServiceResponse.from(product);
    }

    public List<ProductServiceResponse> findAllByPage(final PageServiceRequest request) {
        final Page page = Page.of(request.getNumber(), request.getSize());
        final List<Product> products = productDao.findProducts(page);
        return products.stream()
                .map(ProductServiceResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(final Long productId) {
        findProduct(productId);
        productDao.delete(productId);
    }

    private Product findProduct(final Long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(DataNotFoundException::new);
    }
}
