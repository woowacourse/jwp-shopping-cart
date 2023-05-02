package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.entity.ProductEntity;
import cart.dto.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<ProductResponse> findAll() {
        final List<ProductEntity> result = productDao.findAll();

        return result.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public Long register(final ProductDto productDto) {
        final ProductEntity productEntity = new ProductEntity(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());

        return productDao.insert(productEntity);
    }

    public void updateProduct(final long id, final ProductDto productDto) {
        validateExistData(id);

        final ProductEntity newProductEntity = new ProductEntity(id, productDto.getName(), productDto.getPrice(), productDto.getImageUrl());

        productDao.update(newProductEntity);
    }

    public void deleteProduct(final long id) {
        validateExistData(id);

        productDao.delete(id);
    }

    private void validateExistData(final long id) {
        final Optional<ProductEntity> result = productDao.findById(id);

        if (result.isEmpty()) {
            throw new IllegalArgumentException("찾는 상품은 없는 상품입니다.");
        }
    }
}
