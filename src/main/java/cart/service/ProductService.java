package cart.service;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductService {

    private final ProductDao productDao;
    private final CartDao cartDao;

    public ProductService(final ProductDao productDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    public Long save(final ProductRequest productRequest) {
        Product product = Product.from(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::from)
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
        return productDao.update(id, newProduct);
    }

    public void delete(final Long id) {
        productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id 값이 없습니다."));

        cartDao.deleteByProductId(id);
        productDao.deleteById(id);
    }
}
