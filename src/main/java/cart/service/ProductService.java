package cart.service;

import cart.dto.request.ProductRequestDto;
import cart.entity.ProductEntity;
import cart.repository.ProductDao;
import cart.service.converter.ProductConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;
    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public int create(final ProductRequestDto productRequestDto) {
        final ProductEntity productEntity = ProductConverter.requestDtoToEntity(productRequestDto);
        return productDao.create(productEntity);
    }

    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    public void update(final ProductRequestDto productRequestDto, final int id) {
        productDao.update(productRequestDto, id);
    }

    public void delete(final int id) {
        productDao.delete(id);
    }
}
