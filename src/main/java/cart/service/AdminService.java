package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequest;
import cart.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final ProductDao productDao;

    @Autowired
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

    public void updateProduct(ProductRequest productRequest, int productId) {
        ProductEntity productEntity = new ProductEntity(productId, productRequest.getName(), productRequest.getPrice(), productRequest.getImage());
        productDao.updateProduct(productEntity);
    }

    public void deleteProduct(int productId) {
        productDao.deleteProduct(productId);
    }
}
