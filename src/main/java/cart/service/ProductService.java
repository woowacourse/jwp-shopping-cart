package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductResponseDto;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import cart.dto.entity.ProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private static final String NOT_EXIST_PRODUCT = "해당 상품이 존재하지 않습니다.";
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void addProduct(ProductSaveRequestDto productSaveRequestDto) {
        Product product = new Product(
                productSaveRequestDto.getName(),
                productSaveRequestDto.getImage(),
                productSaveRequestDto.getPrice());

        productDao.save(
                new ProductEntity(product.getId(),
                        product.getName(),
                        product.getImage(),
                        product.getPrice()));
    }

    public List<ProductResponseDto> findProducts() {
        List<ProductEntity> products = productDao.findAll();
        return products.stream()
                .map(entity -> new ProductResponseDto(
                        entity.getId(),
                        entity.getName(),
                        entity.getImage(),
                        entity.getPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateProduct(Long id, ProductUpdateRequestDto productUpdateRequestDto) {
        validateExistence(id);
        Product product = new Product(
                productUpdateRequestDto.getName(),
                productUpdateRequestDto.getImage(),
                productUpdateRequestDto.getPrice());

        productDao.update(new ProductEntity(
                id, product.getName(),
                product.getImage(),
                product.getPrice()));
    }

    @Transactional
    public void deleteProduct(Long id) {
        validateExistence(id);

        productDao.delete(id);
    }

    private void validateExistence(Long id) {
        if (!productDao.existById(id)) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT);
        }
    }
}
