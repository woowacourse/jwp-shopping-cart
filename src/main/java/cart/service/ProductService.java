package cart.service;

import cart.domain.Product;
import cart.dto.CreateProductRequest;
import cart.dto.ProductDto;
import cart.dto.UpdateProductRequest;
import cart.repository.dao.ProductDao;
import cart.repository.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void addProduct(final CreateProductRequest request) {
        final Product product = new Product(request.getName(), request.getImageUrl(), request.getPrice());
        productDao.save(ProductEntity.from(product));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAllProduct() {
        return productDao.findAll().stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public void updateProduct(final Long id, final UpdateProductRequest request) {
        final Product product = new Product(request.getName(), request.getImageUrl(), request.getPrice());
        final int affected = productDao.update(ProductEntity.of(id, product));
        if (affected == 0) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    public void deleteProduct(final Long id) {
        final int affected = productDao.delete(id);
        if (affected == 0) {
            throw new EmptyResultDataAccessException(1);
        }
    }
}
