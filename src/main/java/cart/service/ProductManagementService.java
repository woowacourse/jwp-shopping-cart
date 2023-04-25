package cart.service;

import cart.dto.ProductDto;
import cart.repository.dao.ProductDao;
import cart.repository.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductManagementService {

    private final ProductDao productDao;

    public ProductManagementService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void addProduct(final ProductDto productDto) {
        productDao.save(ProductEntity.from(productDto));
    }

    public List<ProductDto> findAllProduct() {
        return productDao.findAll().stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public void updateProduct(final ProductDto productDto) {
        productDao.update(ProductEntity.from(productDto));
    }

    public void deleteProduct(final Long id) {
        productDao.delete(id);
    }
}
