package cart.domain.admin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.domain.admin.persistence.dao.ProductDao;
import cart.domain.admin.persistence.entity.ProductEntity;
import cart.web.admin.dto.PostProductRequest;
import cart.web.admin.dto.ProductResponse;
import cart.web.admin.dto.PutProductRequest;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public long create(final PostProductRequest postProductRequest) {
        final ProductEntity productEntity =
            new ProductEntity(postProductRequest.getName(), postProductRequest.getPrice(),
                postProductRequest.getImageUrl());
        return productDao.save(productEntity);
    }

    public List<ProductResponse> readAll() {
        final List<ProductEntity> products = productDao.findAll();
        return products.stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
    }

    public void update(final long id, final PutProductRequest putProductRequest) {
        final ProductEntity productEntity =
            new ProductEntity(id, putProductRequest.getName(), putProductRequest.getPrice(),
                putProductRequest.getImageUrl());
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
