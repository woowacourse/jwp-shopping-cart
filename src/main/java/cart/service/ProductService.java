package cart.service;

import cart.controller.exception.ProductNotFoundException;
import cart.controller.exception.ProductNotValidException;
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
        return productDao.findById(id)
                .orElseThrow(() -> new ProductNotValidException("해당 id를 가진 상품이 존재하지 않습니다."));
    }

    @Transactional
    public Long insert(final CreateProductRequest createProductRequest) {
        final Product product = createProductRequest.toProduct();
        return productDao.insert(product);
    }

    @Transactional
    public int update(final Long id, final UpdateProductRequest updateProductRequest) {
        final Product product = updateProductRequest.toProduct();
        final int updatedRow = productDao.update(id, product);
        if (updatedRow == 0) {
            throw new ProductNotFoundException();
        }
        return updatedRow;
    }

    @Transactional
    public int delete(final Long id) {
        final int affectedRow = productDao.delete(id);
        if (affectedRow == 0) {
            throw new ProductNotFoundException();
        }
        return affectedRow;
    }
}
