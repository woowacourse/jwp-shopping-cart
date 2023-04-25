package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.entity.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long saveProduct(final ProductRequestDto productRequestDto) {
        final Product newProduct = new Product(productRequestDto);
        return productDao.insertProduct(newProduct);
    }

    public List<ProductResponseDto> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
            .map(ProductResponseDto::new)
            .collect(Collectors.toList());
    }
}
