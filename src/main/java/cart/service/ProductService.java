package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(ProductRequest productRequest) {
        final Product product = createProduct(productRequest);
        return productDao.save(product);
    }

    public List<ProductResponse> findAll() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImgUrl())
                )
                .collect(Collectors.toUnmodifiableList());
    }

    public void delete(final Long id) {
        if (doesNotExist(id)) {
            throw new NoSuchElementException("상품을 찾을 수 없습니다.");
        }

        productDao.delete(id);
    }

    private boolean doesNotExist(final Long id) {
        return !productDao.existBy(id);
    }

    public void update(final Long id, final ProductRequest request) {
        productDao.update(id, createProduct(request));
    }

    private Product createProduct(ProductRequest productRequest) {
        return new Product(
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getImgUrl()
        );
    }
}
