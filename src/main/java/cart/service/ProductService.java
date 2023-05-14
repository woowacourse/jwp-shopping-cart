package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.dto.ProductRequest;
import cart.exception.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long createProduct(ProductRequest request) {
        Product product = new Product(request.getName(), request.getImage(), request.getPrice());
        return productDao.insert(product);
    }

    public List<Product> findProducts() {
        return productDao.findAll();
    }

    public Product findProduct(Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 상품이 존재하지 않습니다."));
    }

    public Product updateProduct(Long id, ProductRequest request) {
        findProduct(id); //checkIfExist
        Product updateProduct = new Product(id, request.getName(), request.getImage(), request.getPrice());
        productDao.update(updateProduct);
        return updateProduct;
    }

    public void removeMember(Long id) {
        findProduct(id); //checkIfExist
        productDao.delete(id);
    }

}
