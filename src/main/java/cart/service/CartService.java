package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.exception.notfound.ProductNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
                .ifPresent((cart) -> {
                    throw new IllegalArgumentException("이미 장바구니에 담은 상품입니다.");
                });

        cartDao.insert(Cart.of(member, product));
    }

    @Transactional(readOnly = true)
    public List<Product> findCartProducts(final Member member) {
        List<Cart> carts = cartDao.findAllByMemberId(member.getId());

        if (carts.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> productIds = carts.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        return productDao.findAllByIds(productIds);
    }

    public void delete(final Member member, final long productId) {
        checkExistProductId(productId);
        cartDao.delete(member.getId(), productId);
    }

    private void checkExistProductId(final long productId) {
        Optional<Product> product = productDao.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }
    }
}
