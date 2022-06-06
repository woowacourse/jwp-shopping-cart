package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ProductStock;
import woowacourse.shoppingcart.domain.product.ThumbnailImage;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ThumbnailImageDto;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findProducts() {
        List<ProductStock> products = productDao.findProductStocks();
        return products.stream()
            .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(),
                product.getStockQuantity(),
                new ThumbnailImageDto(product.getThumbnailImageUrl(), product.getThumbnailImageAlt())))
            .collect(Collectors.toList());
    }

    public Long addProduct(final ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
            new ThumbnailImage(productRequest.getThumbnailImageUrl(), productRequest.getThumbnailImageAlt()));
        return productDao.save(product, productRequest.getStockQuantity());
    }

    public ProductResponse findProductById(final Long productId) {
        ProductStock product = productDao.findProductStockById(productId);
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStockQuantity(),
            new ThumbnailImageDto(product.getThumbnailImageUrl(),
                product.getThumbnailImageAlt()));
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
