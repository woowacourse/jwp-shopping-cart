package cart.dao;

import cart.domain.Id;
import cart.domain.ImageUrl;
import cart.domain.Price;
import cart.domain.Product;
import cart.domain.ProductName;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final ProductDao productDao;

    public ProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    //레포지토리와 dao의 메서드 네이밍
    public Long addProduct(Product product) {
        ProductEntity productEntity = convertToProductEntity(product);
        return productDao.insert(productEntity);
    }

    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
                .map(this::convertToProduct)
                .collect(Collectors.toList());
    }

    public Product getProduct(Long id) {
        Optional<ProductEntity> productEntity = productDao.findById(id);
        checkIfExistProduct(productEntity);
        return convertToProduct(productEntity.get());
    }

    //todo : 객체를 update 하는 것이 어색하다고 느껴집니다.
    public Product updateProduct(Product product) {
        checkIfExistProduct(productDao.findById(product.getId()));
        productDao.updateProduct(convertToProductEntity(product));
        return product;
    }

    public void removeProduct(Long productId) {
        checkIfExistProduct(productDao.findById(productId));
        productDao.deleteProduct(productId);
    }

    private void checkIfExistProduct(Optional<ProductEntity> productEntity) {
        if(productEntity.isEmpty()) {
            // todo : 커스텀 예외 던지기
            throw new IllegalArgumentException("해당 상품이 존재하지 않습니다.");
        }
    }

    private ProductEntity convertToProductEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getImageUrl(),
                product.getPrice());
    }

    private Product convertToProduct(ProductEntity entity) {
        return new Product(new Id(entity.getId()),
                new ProductName(entity.getName()),
                new ImageUrl(entity.getImageUrl()),
                new Price(entity.getPrice()));
    }
}
