package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import cart.dto.request.CreateProductRequest;
import cart.dto.request.UpdateProductRequest;
import cart.dto.response.ProductResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        final List<ProductEntity> productEntities = productDao.selectAll();
        return productEntities.stream()
                .map(entity -> new ProductResponse(
                        entity.getId(),
                        entity.getName(),
                        entity.getPrice(),
                        entity.getImage())
                ).collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public ProductEntity findById(final Long id) {
        final Optional<ProductEntity> productEntity = productDao.findById(id);
        if (productEntity.isEmpty()) {
            throw new IllegalArgumentException("해당 id를 가진 상품이 존재하지 않습니다.");
        }
        return productEntity.get();
    }

    @Transactional
    public void insert(final CreateProductRequest createProductRequest) {
        final Product newProduct = new Product(
                createProductRequest.getName(),
                createProductRequest.getPrice(),
                createProductRequest.getImage()
        );
        productDao.insert(newProduct);
    }

    @Transactional
    public void update(final Long id, final UpdateProductRequest updateProductRequest) {
        final Optional<ProductEntity> productEntityOptional = productDao.findById(id);
        if (productEntityOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 데이터입니다.");
        }
        final Product product = new Product(
                updateProductRequest.getName(),
                updateProductRequest.getPrice(),
                updateProductRequest.getImage()
        );
        productDao.update(id, product);
    }

    @Transactional
    public void delete(final Long id) {
        final Optional<ProductEntity> productEntityOptional = productDao.findById(id);
        if (productEntityOptional.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 데이터입니다.");
        }
        productDao.delete(id);
    }
}
