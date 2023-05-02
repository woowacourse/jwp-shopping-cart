package cart.service;

import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import cart.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public int create(final ProductRequestDto productRequestDto) {
        return productDao.create(dtoToEntity(productRequestDto));
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

    private ProductEntity dtoToEntity(final ProductRequestDto productRequestDto) {
        return new ProductEntity(productRequestDto.getName(),
                productRequestDto.getImage(),
                productRequestDto.getPrice());
    }
}
