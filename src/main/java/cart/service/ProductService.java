package cart.service;

import static java.util.stream.Collectors.toUnmodifiableList;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dto.ProductSaveRequestDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(final ProductSaveRequestDto request) {
        final Product product = new Product(request.getName(), request.getImage(), request.getPrice());

        return productDao.saveAndGetId(product)
                .orElseThrow(() -> new IllegalStateException("상품을 저장할 수 없습니다."));
    }

    public List<ProductDto> findAll() {
        return productDao.findAll().stream()
                .map(ProductDto::from)
                .collect(toUnmodifiableList());
    }
}
