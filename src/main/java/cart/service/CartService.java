package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import cart.dto.request.CreateProductRequest;
import cart.dto.request.UpdateProductRequest;
import cart.dto.response.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private static final int MINIMUM_AFFECTED_ROWS = 1;

    private final ProductDao productDao;

    @Autowired
    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

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

    public void insert(final CreateProductRequest createProductRequest) {
        final Product newProduct = new Product(
                createProductRequest.getName(),
                createProductRequest.getPrice(),
                createProductRequest.getImage()
        );
        productDao.insert(newProduct);
    }

    public void update(final Long id, final UpdateProductRequest updateProductRequest) {
        final Product product = new Product(
                updateProductRequest.getName(),
                updateProductRequest.getPrice(),
                updateProductRequest.getImage()
        );
        final int updatedRows = productDao.update(id, product);
        validateAffectedRowsCount(updatedRows);
    }

    private void validateAffectedRowsCount(final int affectedRows) {
        if (affectedRows < MINIMUM_AFFECTED_ROWS) {
            throw new IllegalArgumentException("접근하려는 데이터가 존재하지 않습니다.");
        }
    }

    public void delete(final Long id) {
        final int affectedRows = productDao.delete(id);
        validateAffectedRowsCount(affectedRows);
    }
}
