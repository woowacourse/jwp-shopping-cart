package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import cart.service.dto.ProductDto;
import cart.service.dto.ProductInfoDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductInfoDto> findAllProducts() {
        List<ProductEntity> allProducts = productDao.findAll();
        return allProducts.stream()
                .map(ProductInfoDto::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    public long save(final ProductDto productDto) {
        Product product = productDto.toDomain();
        return productDao.insert(ProductEntity.ofDomain(product));
    }

    public void modifyById(final ProductDto productDto, final long id) {
        Product product = productDto.toDomain();
        productDao.update(ProductEntity.fromDomainAndId(product, id));
    }

    public void removeById(final long id) {
        productDao.delete(id);
    }
}
