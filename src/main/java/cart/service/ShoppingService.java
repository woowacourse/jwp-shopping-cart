package cart.service;

import cart.controller.dto.ProductDto;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                        product.getPrice(), product.getCategory())).collect(Collectors.toList());
    }

    public void save(final ProductDto productDto) {
        productDao.insert(new Product(productDto.getId(), productDto.getName(), productDto.getImageUrl(),
                productDto.getPrice(), productDto.getCategory()));
    }

    public void update(final Long id, final ProductDto productDto) {
        int updatedCount = productDao.update(new Product(id, productDto.getName(), productDto.getImageUrl(),
                productDto.getPrice(), productDto.getCategory()));
        if (updatedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public void delete(final Long id) {
        int deletedCount = productDao.deleteById(id);
        if (deletedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public ProductDto getById(final Long id) {
        final Optional<Product> product = productDao.findById(id);
        if (product.isEmpty()) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        final Product findProduct = product.get();
        return new ProductDto(findProduct.getId(), findProduct.getName(), findProduct.getImageUrl(),
                findProduct.getPrice(), findProduct.getCategory());
    }
}
