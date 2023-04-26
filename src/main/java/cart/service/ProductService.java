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

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void create(ProductCreateRequest request) {
        request.validatePrice(request.getPrice());
        productDao.create(request);
    }

    public List<ProductResponse> findAll() {
        return productDao.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, ProductUpdateRequest request) {
        request.validatePrice(request.getPrice());
        productDao.updateById(id, request);
    }

    @Transactional
    public void delete(Long id) {
        productDao.deleteById(id);
    }

}
