package cart.service;

import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductResponseDto;
import cart.entity.ProductEntity;
import cart.repository.CartDao;
import cart.repository.ProductDao;
import cart.service.converter.ProductConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final CartDao cartDao;

    public ProductService(final ProductDao productDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    public int create(final ProductRequestDto productRequestDto) {
        final ProductEntity productEntity = ProductConverter.requestDtoToEntity(productRequestDto);
        return productDao.create(productEntity);
    }

    public List<ProductResponseDto> findAll() {
        List<ProductEntity> entities = productDao.findAll();
        return ProductConverter.entitiesToResponseDtos(entities);
    }

    public void update(final ProductRequestDto productRequestDto, final int id) {
        productDao.update(productRequestDto, id);
    }

    @Transactional
    public void delete(final int id) {
        productDao.delete(id);
        cartDao.deleteByProductId(id);
    }
}
