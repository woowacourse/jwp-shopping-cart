package cart.service;

import cart.annotation.ServiceWithTransactionalReadOnly;
import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.controller.dto.response.ProductResponse;
import cart.dao.ProductDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ServiceWithTransactionalReadOnly
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void create(final ProductCreateRequest request) {
        request.validatePrice(request.getPrice());
        productDao.create(request.toEntity());
    }

    public List<ProductResponse> findAll() {
        return productDao.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(final Long id, final ProductUpdateRequest request) {
        request.validatePrice(request.getPrice());
        productDao.updateById(id, request.toEntity());
    }

    @Transactional
    public void delete(final Long id) {
        productDao.deleteById(id);
    }

}
