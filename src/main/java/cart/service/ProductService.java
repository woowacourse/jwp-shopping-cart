package cart.service;

import cart.dto.request.ProductCreateDto;
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

    public int create(final ProductCreateDto productCreateDto) {
        return productDao.create(dtoToEntity(productCreateDto));
    }

    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    public void update(final ProductCreateDto productCreateDto, final int id) {
        productDao.update(productCreateDto, id);
    }

    public void delete(final int id) {
        productDao.delete(id);
    }

    private ProductEntity dtoToEntity(final ProductCreateDto productCreateDto) {
        return new ProductEntity(productCreateDto.getName(),
                productCreateDto.getImage(),
                productCreateDto.getPrice());
    }
}
