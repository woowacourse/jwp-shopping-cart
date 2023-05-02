package cart.service;

import cart.controller.dto.request.ProductCreateRequest;
import cart.controller.dto.request.ProductUpdateRequest;
import cart.controller.dto.response.ProductResponse;
import cart.convertor.ProductEntityConvertor;
import cart.dao.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;
    private final ProductEntityConvertor productEntityConvertor;

    public ProductService (ProductDao productDao, final ProductEntityConvertor productEntityConvertor) {
        this.productDao = productDao;
        this.productEntityConvertor = productEntityConvertor;
    }

    @Transactional
    public void create (ProductCreateRequest request) {
        productDao.create(productEntityConvertor.dtoToEntity(request));
    }

    public List<ProductResponse> findAll () {
        return productDao.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update (Long id, ProductUpdateRequest request) {
        productDao.updateById(id, productEntityConvertor.dtoToEntity(request));
    }

    @Transactional
    public void delete (Long id) {
        productDao.deleteById(id);
    }

}
