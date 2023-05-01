package cart.service;

import cart.domain.Product;
import cart.dto.CreateProductRequest;
import cart.dto.ProductDto;
import cart.dto.UpdateProductRequest;
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

    public void addProduct(final CreateProductRequest request) {
        final Product product = new Product(request.getName(), request.getImageUrl(), request.getPrice());
        productDao.save(ProductEntity.from(product));
    }

    public List<ProductDto> findAllProduct() {
        return productDao.findAll().stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public void updateProduct(final Long id, final UpdateProductRequest request) {
        final Product product = new Product(request.getName(), request.getImageUrl(), request.getPrice());
        productDao.update(ProductEntity.of(id, product));
    }

    public void deleteProduct(final Long id) {
        productDao.delete(id);
    }
}
