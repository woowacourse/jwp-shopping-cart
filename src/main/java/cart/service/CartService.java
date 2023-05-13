package cart.service;

import cart.controller.dto.response.CartResponse;
import cart.controller.dto.response.ProductResponse;
import cart.dao.CartDao;
import cart.dao.CartEntity;
import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.domain.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final ProductDao productDao;
    private final CartDao cartDao;

    public CartService(final ProductDao productDao, final CartDao cartDao) {
        this.productDao = productDao;
        this.cartDao = cartDao;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findAll(final Integer memberId) {
        List<CartEntity> cartEntities = cartDao.findCartByMemberId(memberId);
        return cartEntities.stream()
                .map(cartEntity -> {
                    int productId = cartEntity.getProductId();
                    Product product = makeProduct(productDao.findById(productId).get());
                    ProductResponse productResponse = new ProductResponse(product.getId(), product.getName(),
                            product.getImageUrl(), product.getPrice());
                    return new CartResponse(cartEntity.getId(), productResponse);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void insertCart(final int productId, final Integer memberId) {
        checkExistProduct(productId);

        cartDao.insertCart(makeCartEntity(productId, memberId));
    }

    @Transactional
    public void deleteCart(final int cartId, final int memberId) {
        cartDao.deleteById(cartId, memberId);
    }

    private void checkExistProduct(final int id) {
        productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품 id를 확인해주세요."));
    }

    private CartEntity makeCartEntity(final int productId, final Integer memberId) {
        return new CartEntity(productId, memberId);
    }

    private Product makeProduct(final ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(),
                productEntity.getPrice());
    }

}
