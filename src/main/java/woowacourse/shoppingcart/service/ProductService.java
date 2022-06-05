package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        List<Product> products = productDao.findProducts();
        return products.stream()
            .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl()))
            .collect(Collectors.toList());
    }

    public Long addProduct(final ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
            productRequest.getThumbnailImage().getUrl());
        return productDao.save(product);
    }

    public ProductResponse findProductById(final Long productId) {
        Product product = productDao.findProductById(productId);
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
