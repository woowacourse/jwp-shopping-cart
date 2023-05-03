package cart.service;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.persistence.dao.Dao;
import cart.persistence.entity.ProductEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    public static final int EXPECTED_SIZE = 1;
    private final Dao productDao;

    public CartService(final Dao productDao) {
        this.productDao = productDao;
    }

    public long create(final ProductRequest productRequest) {
        final ProductEntity productEntity =
                new ProductEntity(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        return productDao.save(productEntity);
    }

    public List<ProductResponse> readAll() {
        final List<ProductEntity> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public void update(final long id, final ProductRequest productRequest) {
        final ProductEntity productEntity =
                new ProductEntity(id, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
        if (productDao.update(productEntity) != EXPECTED_SIZE) {
            throw new EmptyResultDataAccessException(EXPECTED_SIZE);
        }
        ;
    }

    public void delete(final long id) {
        if (productDao.deleteById(id) != EXPECTED_SIZE) {
            throw new EmptyResultDataAccessException(EXPECTED_SIZE);
        }
        ;
    }
}
