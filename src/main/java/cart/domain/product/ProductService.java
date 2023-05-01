package cart.domain.product;

import cart.web.controller.product.dto.ProductRequest;
import cart.web.controller.product.dto.ProductResponse;
import cart.web.exception.ErrorCode;
import cart.web.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public final Logger log = LoggerFactory.getLogger(getClass());

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> getProducts() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getProductNameValue(), product.getImageUrlValue(), product.getPriceValue(), product.getCategory()))
                .collect(Collectors.toList());
    }

    public Long save(final ProductRequest productRequest) {
        final Product product = new Product(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice(), productRequest.getCategory());
        return productDao.insert(product);
    }

    public void update(final Long id, final ProductRequest productRequest) {
        final Product product = new Product(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice(),
                productRequest.getCategory());
        int updatedCount = productDao.update(id, product);
        if (updatedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public void delete(final Long id) {
        int deletedCount = productDao.deleteById(id);
        if (deletedCount == 0) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        if (deletedCount > 1) {
            log.error("error = {}", "delete count is more than 2");
            throw new GlobalException(ErrorCode.INVALID_DELETE);
        }
    }

    public ProductResponse getById(final Long id) {
        final Optional<Product> productById = productDao.findById(id);
        if (productById.isEmpty()) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        final Product product = productById.get();
        return new ProductResponse(product.getId(), product.getProductNameValue(), product.getImageUrlValue(),
                product.getPriceValue(), product.getCategory());
    }
}
