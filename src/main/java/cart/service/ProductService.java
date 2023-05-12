package cart.service;

import cart.controller.exception.ProductNotFoundException;
import cart.controller.exception.ProductNotValidException;
import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductDto;
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
        final List<Product> products = productDao.findAll();
        if (products.isEmpty()) {
            return new ArrayList<>();
        }
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public Product findById(final Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new ProductNotValidException("해당 id를 가진 상품이 존재하지 않습니다."));
    }

    @Transactional
    public Long insert(final ProductDto productDto) {
        final Product product = productDto.toProduct();
        return productDao.insert(product);
    }

    @Transactional
    public int update(final Long id, final ProductDto productDto) {
        final Product product = productDto.toProduct();
        final int updatedRow = productDao.update(id, product);
        if (updatedRow == 0) {
            throw new ProductNotFoundException();
        }
        return updatedRow;
    }

    @Transactional
    public int delete(final Long id) {
        final int deletedRow = productDao.delete(id);
        if (deletedRow == 0) {
            throw new ProductNotFoundException();
        }
        return deletedRow;
    }
}
