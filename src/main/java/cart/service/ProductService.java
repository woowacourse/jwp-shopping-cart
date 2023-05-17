package cart.service;

import cart.dao.ProductDao;
import cart.domain.ProductEntity;
import cart.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    public void insert(final ProductDto productDto) {
        ProductEntity productEntity =
                new ProductEntity.Builder()
                        .name(productDto.getName())
                        .image(productDto.getImage())
                        .price(productDto.getPrice())
                        .build();
        productDao.insert(productEntity);
    }

    public void update(final int id, final ProductDto productDto) {
        ProductEntity productEntity = findProductById(id);

        ProductEntity updatedEntity = productEntity.update(productDto);

        productDao.update(updatedEntity);
    }

    public ProductEntity findProductById(final int id) {
        return productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 id를 확인해주세요."));
    }

    public void delete(final int id) {
        findProductById(id);
        productDao.delete(id);
    }

}
