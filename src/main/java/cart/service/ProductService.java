package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import java.util.List;
import java.util.stream.Collectors;

import cart.dto.ProductDto;
import cart.dto.request.ProductSaveRequest;
import cart.dto.request.ProductUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public long saveProduct(ProductSaveRequest request) {
        Product product = new Product(request.getName(), request.getImage(), request.getPrice());
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAllProducts() {
        List<Product> products = productDao.findAllProducts();
        return products.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    public void updateProduct(ProductUpdateRequest request) {
        Product product = new Product(request.getProductId(), request.getName(), request.getImage(), request.getPrice());
        productDao.updateProduct(product);
    }

    public void deleteProduct(long productId) {
        productDao.deleteProduct(productId);
    }
}
