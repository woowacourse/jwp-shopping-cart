package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponseDto> findAll() {
        return productDao.findAll()
                .stream()
                .map(this::toProductResponseDto)
                .collect(Collectors.toList());
    }

    private ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(product);
    }

}
