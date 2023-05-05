package cart.dao;

import cart.domain.Id;
import cart.domain.product.ImageUrl;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.domain.product.ProductName;
import cart.exception.NotFoundException;
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

    //todo 질문 : 객체를 update 하는 것이 어색하다고 느껴집니다.
    public Product updateProduct(Product product) {
        checkIfExistProduct(productDao.findById(product.getId()));
        productDao.update(convertToProductEntity(product));
        return product;
    }

    public void removeProduct(Long productId) {
        checkIfExistProduct(productDao.findById(productId));
        productDao.delete(productId);
    }

    private void checkIfExistProduct(Optional<ProductEntity> productEntity) {
        if(productEntity.isEmpty()) {
            throw new NotFoundException("해당 상품이 존재하지 않습니다.");
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
