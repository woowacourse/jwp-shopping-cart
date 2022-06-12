package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public long add(ProductRequest request) {
        Product product = new Product(request.getName(), request.getPrice(), request.getImageUrl());
        return productDao.save(product);
    }

    public ProductResponse findProduct(long productId) {
        Product product = validateExistProduct(productDao.findProductById(productId));
        return new ProductResponse(product);
    }

    public List<ProductResponse> findProducts() {
        return productDao.findProducts()
                .stream().map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    private Product validateExistProduct(Optional<Product> product) {
        return product.orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public void deleteProduct(long productId) {
        productDao.delete(productId);
    }
}
