package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.entity.ProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cart.service.ProductMapper.productToEntity;
import static cart.service.ProductMapper.requestDtoToProduct;

@Service
@Transactional(readOnly = true)
public class CartService {

    private static final String NOT_EXIST_PRODUCT = "해당 상품이 존재하지 않습니다.";
    private final ProductDao productDao;

    public CartService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void addProduct(ProductRequestDto productRequestDto) {
        Product product = requestDtoToProduct(productRequestDto);

        productDao.save(productToEntity(product));
    }

    public List<ProductResponseDto> findProducts() {
        List<ProductEntity> products = productDao.findAll();
        return products.stream()
                .map(ProductMapper::entityToResponseDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateProduct(ProductRequestDto productRequestDto) {
        validateExistence(productRequestDto.getId());

        Product product = requestDtoToProduct(productRequestDto);
        productDao.update(productToEntity(product));
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
