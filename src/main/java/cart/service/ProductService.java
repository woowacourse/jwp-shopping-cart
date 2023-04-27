package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long create(ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        Long savedId = productDao.save(product);
        return savedId;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::new)
                .collect(toList());
    }

    @Transactional
    public void deleteById(Long id) {
        productDao.deleteById(id);
    }

    @Transactional
    public void update(Long productId, ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        productDao.updateById(productId, product);
    }
}
