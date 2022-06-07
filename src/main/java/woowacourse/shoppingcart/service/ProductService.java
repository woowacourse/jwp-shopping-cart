package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }

    public int addProducts(List<ProductRequestDto> productDtos) {
        List<Product> products = productDtos.stream()
                .map(dto -> new Product(dto.getName(), dto.getPrice(), dto.getThumbnailUrl()))
                .collect(Collectors.toList());
        return productDao.saveAll(products);
    }
}
