package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Page;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.PageRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductSaveRequest;
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
    public Long addProduct(final ProductSaveRequest request) {
        final Product product = new Product(request.getName(), request.getPrice(), request.getImageUrl());
        return productDao.save(product);
    }

    public ProductResponse findById(final Long productId) {
        final Product product = findProduct(productId);
        return new ProductResponse(productId, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public List<ProductResponse> findAllByPage(final PageRequest request) {
        final Page page = Page.of(request.getPage(), request.getSize());
        final List<Product> products = productDao.findProducts(page);

        return products.stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getImageUrl()))
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
