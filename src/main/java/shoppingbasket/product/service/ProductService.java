package shoppingbasket.product.service;

import shoppingbasket.product.dao.ProductDao;
import shoppingbasket.product.dto.ProductInsertRequestDto;
import shoppingbasket.product.dto.ProductUpdateRequestDto;
import shoppingbasket.product.entity.ProductEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductEntity addProduct(final ProductInsertRequestDto productInsertRequestDto) {
        final ProductEntity product = ProductMapper.toEntity(productInsertRequestDto);
        return productDao.insert(product);
    }

    public List<ProductEntity> getProducts() {
        return productDao.selectAll();
    }

    public int updateProduct(final ProductUpdateRequestDto productUpdateRequestDto) {
        final ProductEntity product = ProductMapper.toEntity(productUpdateRequestDto);
        return productDao.update(product);
    }

    public int deleteProduct(final int productId) {
       return productDao.delete(productId);
    }

    public ProductEntity findProductById(final int id) {
        return productDao.findById(id);
    }
}
