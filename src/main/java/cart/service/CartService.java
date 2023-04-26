package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void addProduct(ProductRequestDto productRequestDto) {
        ProductDto product = new ProductDto(productRequestDto.getName(), productRequestDto.getImage(), productRequestDto.getPrice());
        productDao.save(product);
    }

    public List<ProductResponseDto> findProducts() {
        List<ProductDto> products = productDao.findAll();
        return products.stream()
                .map(product -> new ProductResponseDto(product.getId(), product.getName(), product.getImage(), product.getPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateProduct(ProductRequestDto productRequestDto) {
        ProductDto product = new ProductDto(productRequestDto.getId(), productRequestDto.getName(), productRequestDto.getImage(), productRequestDto.getPrice());
        productDao.update(product);
    }

    public void deleteProduct(Long id) {
        productDao.delete(id);
    }
}
