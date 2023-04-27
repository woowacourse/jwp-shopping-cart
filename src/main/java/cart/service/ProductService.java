package cart.service;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.domain.Product;
import cart.dto.ProductDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDaoImpl;

    public ProductService(final ProductDao productDao) {
        this.productDaoImpl = productDao;
    }

    public List<ProductDto> findAll() {
        List<ProductEntity> productEntities = productDaoImpl.findAll();
        return productEntities.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    public Integer insert(final ProductDto productDto) {
        Product product = productDto.toProduct();
        ProductEntity productEntity = product.toEntity();
        return productDaoImpl.insert(productEntity);
    }

    public void update(final int id, final ProductDto productDto) {
        ProductEntity productEntity = findProductById(id);
        Product product = productDto.toProduct();

        ProductEntity updatedEntity = productEntity.update(product.toEntity());

        productDaoImpl.update(updatedEntity);
    }

    private ProductEntity findProductById(final int id) {
        return productDaoImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 id를 확인해주세요."));
    }

    public void delete(final int id) {
        findProductById(id);
        productDaoImpl.delete(id);
    }

}
