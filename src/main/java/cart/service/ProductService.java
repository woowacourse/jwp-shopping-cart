package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int addProduct(ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getPrice(),
                productRequest.getImage());
        return productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> selectAllProducts() {
        final List<Product> products = productDao.selectAll();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateProduct(ProductRequest productRequest, int productId) {
        Product product = new Product(productId, productRequest.getName(),
                productRequest.getPrice(), productRequest.getImage());
        int updatedResult = productDao.update(product);
        if (updatedResult == 0) {
            throw new IllegalStateException("존재하지 않는 상품입니다.");
        }
    }

    public void deleteProduct(int productId) {
        int deletedResult = productDao.delete(productId);
        if (deletedResult == 0) {
            throw new IllegalStateException("존재하지 않는 상품입니다.");
        }
    }

}
