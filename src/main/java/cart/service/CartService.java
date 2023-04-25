package cart.service;

import cart.dao.ProductDao;
import cart.dto.InsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.UpdateRequestDto;
import cart.entity.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void addProduct(final InsertRequestDto insertRequestDto) {
        productDao.insert(insertRequestDto.toEntity());
    }

    public List<ProductResponseDto> getProducts() {
        final List<ProductEntity> products = productDao.selectAll();

        return products.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getImage(),
                        product.getName(),
                        product.getPrice()
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateProduct(final UpdateRequestDto updateRequestDto) {
        productDao.update(updateRequestDto.toEntity());
    }
}
