package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequestDto;
import cart.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long saveProduct(final ProductRequestDto productRequestDto) {
        final Product newProduct = new Product(productRequestDto);
        return productDao.insertProduct(newProduct);
    }
}
