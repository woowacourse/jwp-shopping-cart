package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ProductStock;
import woowacourse.shoppingcart.domain.product.vo.ThumbnailImage;
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

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts() {
        List<ProductStock> products = productDao.findProductStocks();
        return products.stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
    }

    public ProductResponse addProduct(final ProductRequest productRequest) {
        ThumbnailImageDto thumbnailImageDto = productRequest.getThumbnailImage();
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
            new ThumbnailImage(thumbnailImageDto.getUrl(), thumbnailImageDto.getAlt()));
        ProductStock addedProductStock = productDao.save(product, productRequest.getStockQuantity());
        return ProductResponse.from(addedProductStock);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(final Long productId) {
        ProductStock product = productDao.findProductStockById(productId);
        return ProductResponse.from(product);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
