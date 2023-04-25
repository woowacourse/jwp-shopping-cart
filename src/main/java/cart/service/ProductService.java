package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.request.ProductRequest;
import cart.dto.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void create(ProductRequest productRequest) {
        productDao.save(productRequest);
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::new)
                .collect(toList());
    }

    public void deleteById(Long id) {
        productDao.deleteById(id);
    }

    public void update(Long productId, ProductRequest productRequest) {
        productDao.updateById(productId, productRequest);
    }
}
