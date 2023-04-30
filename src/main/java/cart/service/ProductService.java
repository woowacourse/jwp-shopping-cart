package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dto.request.ProductSaveRequest;
import cart.dto.request.ProductUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public void updateProduct(long productId, ProductUpdateRequest request) {
        Product product = new Product(productId, request.getName(), request.getImage(), request.getPrice());
        productDao.updateProduct(product);
    }

    public void deleteProduct(long productId) {
        productDao.deleteProduct(productId);
    }
}
