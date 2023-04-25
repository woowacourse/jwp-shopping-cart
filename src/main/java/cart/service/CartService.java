package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.ProductEntity;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void create(final ProductRequest productRequest) {
        final ProductEntity productEntity = new ProductEntity(productRequest.getName(), productRequest.getImage(),
            productRequest.getPrice());
        productDao.save(productEntity);
    }

    public List<ProductResponse> read() {
        final List<ProductEntity> products = productDao.findAll();
        return products.stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
    }

    public void update(final ProductRequest productRequest) {
        final ProductEntity originalEntity = productDao.findByName(productRequest.getName());
        final ProductEntity productEntity = new ProductEntity(originalEntity.getId(), productRequest.getName(),
            productRequest.getImage(), productRequest.getPrice());
        productDao.update(productEntity);
    }

    public void delete(final ProductRequest productRequest) {
        productDao.deleteByName(productRequest.getName());
    }
}
