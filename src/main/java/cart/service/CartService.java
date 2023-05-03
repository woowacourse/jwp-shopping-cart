package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.exception.notfound.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public void addProduct(final Member member, final long productId) {
        Product product = productDao.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        cartDao.findByMemberIdAndProductId(member.getId(), productId)
                .ifPresentOrElse(
                        cart -> {
                            cart.plusQuantity();
                            cartDao.updateQuantity(cart);
                        },
                        () -> cartDao.insert(Cart.of(member, product))
                );
    }

    @Transactional(readOnly = true)
    public List<Product> findCartProducts(final Member member) {
        List<Cart> carts = cartDao.findAllByMemberId(member.getId());

        List<Long> productIds = carts.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        return productDao.findAllByIds(productIds);
    }
}
