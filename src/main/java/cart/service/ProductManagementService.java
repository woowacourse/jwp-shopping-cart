package cart.service;

import cart.dto.ProductDto;
import cart.entity.Product;
import cart.exception.customExceptions.DataNotFoundException;
import cart.repository.dao.productDao.ProductDao;
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

        return productDao.save(new Product(name, imageUrl, price));
    }

    public List<ProductDto> findAllProduct() {
        return productDao.findAll().stream()
                .map(product -> {
                    final Long id = product.getId();
                    final String name = product.getName();
                    final String imageUrl = product.getImageUrl();
                    final int price = product.getPrice();
                    return new ProductDto(id, name, imageUrl, price);
                })
                .collect(Collectors.toList());
    }

    public void updateProduct(final ProductDto productDto) {
        final Long id = productDto.getId();
        final String name = productDto.getName();
        final String imageUrl = productDto.getImageUrl();
        final int price = productDto.getPrice();

        int amountOfUpdatedProduct = productDao.update(new Product(id, name, imageUrl, price));
        if (amountOfUpdatedProduct == 0) {
            throw new DataNotFoundException("해당 상품을 찾을 수 없습니다.");
        }
    }

    public void deleteProduct(final Long id) {
        int amountOfDeletedProduct = productDao.delete(id);
        if (amountOfDeletedProduct == 0) {
            throw new DataNotFoundException("해당 상품을 찾을 수 없습니다.");
        }
    }
}
