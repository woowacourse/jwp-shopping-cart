package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import cart.controller.domain.Product;
import cart.controller.dto.ProductModifyRequest;
import cart.controller.dto.ProductRegisterRequest;
import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.service.dto.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ProductDao productDao;

    public CartService(final ProductDao productDao) {
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
        ProductEntity productEntity = new ProductEntity.Builder()
                .id(id)
                .name(product.getName())
                .imgUrl(product.getImgUrl())
                .price(product.getPrice())
                .build();
        productDao.update(productEntity);
    }

    public void removeById(long id) {
        productDao.delete(id);
    }
}
