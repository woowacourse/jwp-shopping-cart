package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.service.dto.ProductSaveDto;
import cart.service.dto.ProductUpdateDto;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void save(ProductSaveDto productSaveDto) {
        Product product = Product.createToSave(
                productSaveDto.getName(),
                productSaveDto.getPrice(),
                productSaveDto.getImageUrl()
        );
        this.productDao.save(product);
    }

    public void update(ProductUpdateDto productUpdateDto) {
        Product product = Product.create(
                productUpdateDto.getId(),
                productUpdateDto.getName(),
                productUpdateDto.getPrice(),
                productUpdateDto.getImageUrl()
        );
        this.productDao.update(product);
    }

    public void deleteById(Long id) {
        this.productDao.deleteById(id);
    }
}
