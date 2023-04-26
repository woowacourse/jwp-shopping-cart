package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static cart.service.ProductMapper.productToEntity;
import static cart.service.ProductMapper.requestDtoToProduct;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(ProductDao productDao) {
        this.productDao = productDao;
    }

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

    public void updateProduct(ProductRequestDto productRequestDto) {
        Product product = requestDtoToProduct(productRequestDto);

        productDao.update(productToEntity(product));
    }

    public void deleteProduct(Long id) {
        productDao.delete(id);
    }
}
