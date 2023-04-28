package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
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
    public void addProduct(ProductRequestDto productRequestDto) {
        productDao.save(productRequestDto.toEntity());
    }

    public List<ProductResponseDto> findProducts() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateProduct(ProductRequestDto productRequestDto) {
        validateExistence(productRequestDto.getId());

        productDao.update(productRequestDto.toEntity());
    }

    private void validateExistence(Long id) {
        if (!productDao.existById(id)) {
            throw new IllegalArgumentException(NOT_EXIST_PRODUCT);
        }
    }

    public void deleteProduct(Long id) {
        validateExistence(id);

        productDao.delete(id);
    }
}
