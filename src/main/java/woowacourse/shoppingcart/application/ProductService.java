package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Page;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.dto.response.ProductsResponse;
import woowacourse.exception.notFound.InvalidProductException;

@Service
@Transactional
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public ProductsResponse findProducts(final Integer begin, final Integer size) {
        final Page page = Page.of(begin, size);
        final List<ProductResponse> products = productDao.findProductsByPage(page)
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImageUrl())
                )
                .collect(Collectors.toList());

        final Integer totalCount = productDao.countProducts();
        return new ProductsResponse(totalCount, products);
    }

    public Long addProduct(final ProductRequest productRequest) {
        final Product product = new Product(productRequest.getName(), productRequest.getPrice(),
                productRequest.getImageUrl());
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(final Long productId) {
        final Product product = productDao.findProductById(productId)
                .orElseThrow(InvalidProductException::new);

        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void deleteProductById(final Long productId) {
        if (!productDao.delete(productId)) {
            throw new InvalidProductException();
        }
    }
}
