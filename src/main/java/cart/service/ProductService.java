package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import cart.domain.product.Product;
import cart.dto.ProductModifyRequest;
import cart.dto.ProductRegisterRequest;
import cart.dao.ProductDao;
import cart.entity.ProductEntity;
import cart.dto.ProductResponse;
import cart.exception.ProductNotFoundException;
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

    public long save(ProductRegisterRequest productRegisterRequest) {
        Product product = Product.createBy(productRegisterRequest.getName(), productRegisterRequest.getImgUrl(),
                                            productRegisterRequest.getPrice());
        ProductEntity productEntity = new ProductEntity.Builder()
                .name(product.getName())
                .imgUrl(product.getImgUrl())
                .price(product.getPrice())
                .build();
        return productDao.insert(productEntity);
    }

    public void modifyById(ProductModifyRequest productModifyRequest, long id) {
        Product product = Product.createBy(productModifyRequest.getName(), productModifyRequest.getImgUrl(),
                productModifyRequest.getPrice());

        checkProductExistBy(id);

        ProductEntity productEntity = new ProductEntity.Builder()
                .productId(id)
                .name(product.getName())
                .imgUrl(product.getImgUrl())
                .price(product.getPrice())
                .build();
        productDao.update(productEntity);
    }

    public void removeById(long id) {
        checkProductExistBy(id);
        productDao.delete(id);
    }

    private void checkProductExistBy(long id) {
        if (productDao.isNotExistBy(id)) {
            throw new ProductNotFoundException("상품 ID에 해당하는 상품이 존재하지 않습니다.");
        }
    }
}
