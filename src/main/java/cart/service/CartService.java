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
public class CartService {

    private static final String NOT_EXIST_PRODUCT = "해당 상품이 존재하지 않습니다.";
    private final ProductDao productDao;

    public CartService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long addProduct(ProductSaveRequestDto productSaveRequestDto) {
        Product product = new Product(
                productSaveRequestDto.getName(),
                productSaveRequestDto.getImage(),
                productSaveRequestDto.getPrice());

        ProductEntity save = productDao.save(
                new ProductEntity(product.getId(),
                        product.getName(),
                        product.getImage(),
                        product.getPrice()));

        return save.getId();
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
    public Long updateProduct(Long id, ProductUpdateRequestDto productUpdateRequestDto) {
        validateExistence(id);
        Product product = new Product(
                productUpdateRequestDto.getName(),
                productUpdateRequestDto.getImage(),
                productUpdateRequestDto.getPrice());

        ProductEntity update = productDao.update(new ProductEntity(
                id, product.getName(),
                product.getImage(),
                product.getPrice()));

        return update.getId();
    }

    @Transactional
    public Long deleteProduct(Long id) {
        validateExistence(id);

        productDao.delete(id);
        return id;
    }

    private void validateExistence(Long id) {
        if (!productDao.existById(id)) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT);
        }
    }
}
