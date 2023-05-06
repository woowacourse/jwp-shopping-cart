package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Price;
import cart.domain.Product;
import cart.domain.ProductName;
import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> findAllProducts() {
        List<ProductEntity> allProducts = productDao.findAll();
        return allProducts.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    public long save(final ProductRequest productRequest) {
        Product product = new Product(new ProductName(productRequest.getName()),
                productRequest.getImgUrl(),
                new Price(productRequest.getPrice()));
        return productDao.insert(ProductEntity.ofDomain(product));
    }

    public void modifyById(final ProductRequest productRequest, final long id) {
        Product product = productRequest.toProduct();
        productDao.update(ProductEntity.fromDomainAndId(product, id));
    }

    public void removeById(final long id) {
        productDao.delete(id);
    }
}
