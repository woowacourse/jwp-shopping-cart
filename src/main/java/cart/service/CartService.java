package cart.service;

import cart.controller.dto.NewProductDto;
import cart.controller.dto.ProductDto;
import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ProductDao productDao;

    @Autowired
    public CartService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> findAll() {
        final List<ProductEntity> productEntities = productDao.selectAll();
        return productEntities.stream()
                .map(entity -> new ProductDto(entity.getId(), entity.getName(), entity.getPrice(), entity.getImage()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void insert(final NewProductDto newProductDto) {
        final Product newProduct = new Product(newProductDto.getName(), newProductDto.getPrice(), newProductDto.getImage());
        productDao.insert(newProduct);
    }

    public void update(final ProductDto productDto) {
        final Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImage());
        productDao.update(product, productDto.getId());
    }

    public void delete(final Long id) {
        productDao.delete(id);
    }
}
