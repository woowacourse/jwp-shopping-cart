package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.request.CreateProductRequest;
import cart.dto.request.UpdateProductRequest;
import cart.dto.response.ProductResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        final Optional<List<Product>> productsOptional = productDao.findAll();
        if (productsOptional.isEmpty()) {
            return new ArrayList<>();
        }
        return productsOptional.get().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public Product findById(final Long id) {
        final Optional<Product> product = productDao.findById(id);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("해당 id를 가진 상품이 존재하지 않습니다.");
        }
        return product.get();
    }

    @Transactional
    public Long insert(final CreateProductRequest createProductRequest) {
        final Product product = createProductRequest.toProduct();
        return productDao.insert(product);
    }

    @Transactional
    public int update(final Long id, final UpdateProductRequest updateProductRequest) {
        final Optional<Product> productOptional = productDao.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 데이터입니다.");
        }
        final Product product = updateProductRequest.toProduct();
        return productDao.update(id, product);
    }

    @Transactional
    public int delete(final Long id) {
        final Optional<Product> productOptional = productDao.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 데이터입니다.");
        }
        return productDao.delete(id);
    }
}
