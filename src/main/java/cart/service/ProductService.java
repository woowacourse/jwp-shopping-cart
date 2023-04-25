package cart.service;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.dto.ProductDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    public void insert(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity(productDto.getName(), productDto.getImage(),
                productDto.getPrice());
        productDao.insert(productEntity);
    }
}
