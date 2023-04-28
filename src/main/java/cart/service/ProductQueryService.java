package cart.service;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.service.dto.ProductSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductDao productDao;

    public ProductQueryService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductSearchResponse> searchAllProducts() {
        List<ProductEntity> productEntities = productDao.findAll();

        return productEntities.stream()
                              .map(entity -> new ProductSearchResponse(
                                      entity.getId(),
                                      entity.getName(),
                                      entity.getPrice(),
                                      entity.getImageUrl())
                              )
                              .collect(Collectors.toList());
    }
}
