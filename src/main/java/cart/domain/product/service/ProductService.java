package cart.domain.product.service;

import cart.dao.ProductDao;
import cart.domain.product.entity.Product;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductResponse;
import cart.dto.ProductUpdateRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public ProductResponse create(final ProductCreateRequest productCreateRequest) {
        final Product product = productCreateRequest.makeProduct();
        final Product savedProduct = productDao.save(product);
        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> findAll() {
        final List<Product> products = productDao.findAll();
        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void update(final ProductUpdateRequest productUpdateRequest) {
        final int count = productDao.update(productUpdateRequest.makeProduct());
        checkProductExist(count);
    }

    @Transactional
    public void delete(final Long id) {
        final int count = productDao.delete(id);
        checkProductExist(count);
    }

    private void checkProductExist(final int count) {
        if (count == 0) {
            throw new IllegalArgumentException("존재하지 않는 아이템입니다.");
        }
    }
}
