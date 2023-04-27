package cart.service;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.domain.Product;
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

    public Integer insert(final ProductDto productDto) {
        Product product = productDto.toProduct();
        ProductEntity productEntity = product.toEntity();
        return productDao.insert(productEntity);
    }

    public void update(final int id, final ProductDto productDto) {
        ProductEntity productEntity = findProductById(id);
        Product product = productDto.toProduct();

        ProductEntity updatedEntity = productEntity.update(product.toEntity());

        productDao.update(updatedEntity);
    }

    private ProductEntity findProductById(final int id) {
        return productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 id를 확인해주세요."));
    }

    public void delete(final int id) {
        findProductById(id);
        productDao.delete(id);
    }

}
