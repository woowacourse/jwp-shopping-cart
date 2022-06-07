package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ProductId;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.product.Id;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public ProductService(final ProductDao productDao, CartItemDao cartItemDao) {
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public ProductsResponse findProducts() {
        return new ProductsResponse(getProductsResponse(productDao.getProducts()));
    }

    private List<ProductResponse> getProductsResponse(List<Product> products) {
        final List<ProductId> allProductIdsInCarts = cartItemDao.getAllProductIds();
        return products.stream()
                .map(product ->
                        new ProductResponse(
                                product.getId().getValue(),
                                product.getName().getValue(),
                                product.getPrice().getValue(),
                                product.getThumbnail().getValue(),
                                product.in(allProductIdsInCarts)))
                .collect(Collectors.toList());
    }
}
