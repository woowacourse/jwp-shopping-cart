package cart.service.product;

import cart.dao.product.ProductDao;
import cart.dto.product.ProductRequest;
import cart.dto.product.ProductResponse;
import cart.entity.product.Product;
import cart.exception.notfound.ProductNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long saveProduct(final ProductRequest productRequest) {
        final Product newProduct = productRequest.makeProduct();
        return productDao.insertProduct(newProduct);
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
            .map(ProductResponse::new)
            .collect(Collectors.toList());
    }

    public void updateProduct(final Long id, final ProductRequest productRequest) {
        validateProductExist(id);
        productDao.updateProduct(id, productRequest.makeProduct());
    }

    public Long deleteProduct(final Long id) {
        validateProductExist(id);
        return productDao.deleteProduct(id);
    }

    private void validateProductExist(final Long id) {
        Optional<Product> product = productDao.findById(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException();
        }
    }
}
