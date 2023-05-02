package cart.service;

import cart.controller.dto.ProductDto;
import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(final ProductRequest productRequest) {
        Product product = Product.from(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        return productDao.save(product);
    }

    public List<ProductDto> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductDto::from)
                .collect(Collectors.toList());
    }

    public int update(final Long id, final ProductRequest productRequest) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id 값이 없습니다."));
        Product newProduct = Product.from(
                product.getId(),
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice()
        );
        return productDao.updateById(id, newProduct);
    }

    public void delete(final Long id) {
        productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id 값이 없습니다."));

        productDao.deleteById(id);
    }
}
