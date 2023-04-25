package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequest;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final ProductDao productDao;

    public AdminService(ProductDao productDao) {
        this.productDao = productDao;
    }


    public void registerProduct(ProductRequest productRequest) {
        ProductEntity productEntity = new ProductEntity(productRequest.getName(), productRequest.getPrice(), productRequest.getImage());
        productDao.insertProduct(productEntity);
    }


    public List<ProductEntity> selectAllProducts() {
        return productDao.selectAllProducts();
    }
}
