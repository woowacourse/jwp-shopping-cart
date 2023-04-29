package cart.service;

import cart.dto.ProductDto;
import cart.repository.dao.ProductDao;
import cart.repository.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductManagementService {

    private final ProductDao productDao;

    public ProductManagementService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductDto productDto) {
        final String name = productDto.getName();
        final String imageUrl = productDto.getImageUrl();
        final int price = productDto.getPrice();

        return productDao.save(new ProductEntity(name, imageUrl, price));
    }

    public List<ProductDto> findAllProduct() {
        return productDao.findAll().stream()
                .map(productEntity -> {
                    final Long id = productEntity.getId();
                    final String name = productEntity.getName();
                    final String imageUrl = productEntity.getImageUrl();
                    final int price = productEntity.getPrice();
                    return new ProductDto(id, name, imageUrl, price);
                })
                .collect(Collectors.toList());
    }

    public void updateProduct(final ProductDto productDto) {
        final Long id = productDto.getId();
        final String name = productDto.getName();
        final String imageUrl = productDto.getImageUrl();
        final int price = productDto.getPrice();
        productDao.update(new ProductEntity(id, name, imageUrl, price));
    }

    public void deleteProduct(final Long id) {
        productDao.delete(id);
    }
}
