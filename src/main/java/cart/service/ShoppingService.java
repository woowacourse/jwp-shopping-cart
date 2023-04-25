package cart.service;

import cart.controller.dto.ProductDto;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import cart.persistence.entity.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingService {

    private final ProductDao productDao;

    public ShoppingService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> getProducts() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getImageUrl(),
                        product.getPrice(), product.getCategory().name())).collect(Collectors.toList());
    }

    public void save(final ProductDto productDto) {
        productDao.insert(new Product(productDto.getId(), productDto.getName(), productDto.getImageUrl(),
                productDto.getPrice(), ProductCategory.from(productDto.getCategory())));
    }

    public void update(final Long id, final ProductDto productDto) {
        productDao.findById(id);
        productDao.update(new Product(id, productDto.getName(), productDto.getImageUrl(),
                productDto.getPrice(), ProductCategory.from(productDto.getCategory())));
    }

    public void delete(final Long id) {
        productDao.findById(id);
        productDao.deleteById(id);
    }
}
