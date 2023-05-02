package cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.ProductDao;
import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;

@Service
@Transactional
public class AdminService {
    private final ProductDao productDao;

    @Autowired
    public AdminService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int addProduct(ProductRequestDto productRequestDto) {
        ProductEntity productEntity = new ProductEntity(productRequestDto.getName(), productRequestDto.getPrice(),
            productRequestDto.getImage());
        return productDao.insertProduct(productEntity);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> selectAllProducts() {
        return productDao.selectAllProducts();
    }

    public void updateProduct(ProductRequestDto productRequestDto, int productId) {
        ProductEntity productEntity = new ProductEntity(productId, productRequestDto.getName(),
            productRequestDto.getPrice(), productRequestDto.getImage());
        productDao.updateProduct(productEntity);
    }

    public void deleteProduct(int productId) {
        productDao.deleteProduct(productId);
    }

}
