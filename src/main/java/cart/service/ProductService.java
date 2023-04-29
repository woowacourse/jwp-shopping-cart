package cart.service;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.dao.ProductDao;
import cart.entity.ProductEntity;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductRequest productRequest) {
        final ProductEntity productEntity = new ProductEntity(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice());
        return productDao.save(productEntity);
    }

    public List<ProductResponse> findAll() {
        final List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public ProductResponse findProductById(final Long id) {
        final ProductEntity productEntity = productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("아이디에 해당하는 상품이 없습니다."));
        return ProductResponse.from(productEntity);
    }

    public ProductResponse updateProduct(final Long id, final ProductRequest productRequest) {
        final ProductEntity productEntity = new ProductEntity(
                id,
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice());
        productDao.update(productEntity);
        return ProductResponse.from(productEntity);
    }

    public void deleteProduct(final Long id) {
        productDao.delete(id);
    }
}
