package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.dao.ProductDao;
import cart.dao.entity.Product;
import cart.dto.ProductResponse;
import cart.dto.ProductSaveRequest;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(ProductSaveRequest productSaveRequest) {
        final Product product = new Product(
                productSaveRequest.getName(),
                productSaveRequest.getPrice(),
                productSaveRequest.getImgUrl()
        );
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
        productDao.delete(id);
    }
}
