package cart.service;

import cart.controller.dto.request.product.ProductInsertRequest;
import cart.controller.dto.request.product.ProductUpdateRequest;
import cart.controller.dto.response.ProductResponse;
import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.domain.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
                .map(entity -> new ProductResponse(entity.getId(), entity.getName(), entity.getImageUrl(),
                        entity.getPrice()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Integer insert(final ProductInsertRequest productInsertRequest) {
        Product product = productInsertRequest.toProduct();
        ProductEntity productEntity = makeEntity(product);
        return productDao.insert(productEntity);
    }

    private ProductEntity makeEntity(final Product product) {
        return new ProductEntity(product.getName(), product.getImageUrl(), product.getPrice());
    }

    @Transactional
    public void update(final int id, final ProductUpdateRequest productUpdateRequest) {
        Optional<ProductEntity> findById = productDao.findById(id);
        findProductById(findById);
        Product product = productUpdateRequest.toProduct();
        Product updatedProduct = product.update(productUpdateRequest);

        productDao.update(makeEntity(id, updatedProduct));
    }

    private ProductEntity makeEntity(final int id, final Product product) {
        return new ProductEntity(id, product.getName(), product.getImageUrl(), product.getPrice());
    }

    private void findProductById(final Optional<ProductEntity> findProduct) {
        if (findProduct.isEmpty()) {
            throw new IllegalArgumentException("상품 id를 확인해주세요.");
        }
    }

    @Transactional
    public void delete(final int id) {
        Optional<ProductEntity> findById = productDao.findById(id);
        findProductById(findById);
        productDao.delete(id);
    }

}
