package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long create(ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        return productDao.save(product);
    }

    public List<ProductResponse> findAll() {
        List<ProductEntity> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public ProductResponse update(ProductRequest productRequest, Long id) {
        ProductEntity productEntity = productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + id));
        productEntity.replace(productRequest);
        productDao.update(productEntity);
        return ProductResponse.from(productEntity);
    }

    public void deleteById(Long id) {
        productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + id));
        productDao.deleteById(id);
    }
}
