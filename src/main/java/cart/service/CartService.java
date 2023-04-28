package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.dto.ProductPostRequest;
import cart.dto.ProductPutRequest;
import cart.dto.ProductResponse;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.ProductEntity;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public long create(final ProductPostRequest productPostRequest) {
        final ProductEntity productEntity =
            new ProductEntity(productPostRequest.getName(), productPostRequest.getPrice(),
                productPostRequest.getImageUrl());
        return productDao.save(productEntity);
    }

    public List<ProductResponse> readAll() {
        final List<ProductEntity> products = productDao.findAll();
        return products.stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
    }

    public void update(final long id, final ProductPutRequest productPutRequest) {
        final ProductEntity productEntity =
            new ProductEntity(id, productPutRequest.getName(), productPutRequest.getPrice(), productPutRequest.getImageUrl());
        int affected = productDao.update(productEntity);
        assertRowChanged(affected);
    }

    public void delete(final long id) {
        int affected = productDao.deleteById(id);
        assertRowChanged(affected);
    }

    private void assertRowChanged(final int rowAffected) {
        if (rowAffected < 1) {
            throw new DbNotAffectedException("변경된 정보가 없습니다.");
        }
    }
}
