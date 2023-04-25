package cart.service;

import cart.domain.Product;
import cart.dto.CreateProductRequest;
import cart.persistence.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void create(CreateProductRequest createProductRequest) {
        Product product = new Product(
                createProductRequest.getName(),
                createProductRequest.getImageUrl(),
                createProductRequest.getPrice()
        );

        productDao.create(product);
    }
}
