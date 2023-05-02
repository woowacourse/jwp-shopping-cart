package cart.service;

import cart.controller.dto.request.product.ProductInsertRequest;
import cart.controller.dto.request.product.ProductUpdateRequest;
import cart.controller.dto.response.ProductResponse;
import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.domain.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDaoImpl;

    public ProductService(final ProductDao productDao) {
        this.productDaoImpl = productDao;
    }

    public List<ProductResponse> findAll() {
        List<ProductEntity> productEntities = productDaoImpl.findAll();
        return productEntities.stream()
                .map(entity -> new ProductResponse(entity.getId(), entity.getName(), entity.getImage(),
                        entity.getPrice()))
                .collect(Collectors.toList());
    }

    public Integer insert(final ProductInsertRequest productInsertRequest) {
        Product product = productInsertRequest.toProduct();
        ProductEntity productEntity = product.toEntity();
        return productDaoImpl.insert(productEntity);
    }

    public void update(final int id, final ProductUpdateRequest productUpdateRequest) {
        findProductById(id);
        Product product = productUpdateRequest.toProduct();
        Product updatedProduct = product.update(productUpdateRequest);

        productDaoImpl.update(id, updatedProduct.toEntity());
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
