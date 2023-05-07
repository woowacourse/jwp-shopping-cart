package cart.service;

import cart.dao.ProductsDao;
import cart.dao.CartDao;
import cart.entity.Cart;
import cart.entity.Product;
import cart.entity.vo.Email;
import cart.exception.UserForbiddenException;
import cart.service.dto.ProductInCart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductsDao productsDao;

    public CartService(final CartDao cartDao, final ProductsDao productsDao) {
        this.cartDao = cartDao;
        this.productsDao = productsDao;
    }

    public long addProductToUser(final String userEmail, final long productId) {
        productsDao.findById(productId);
        return cartDao.insert(new Email(userEmail), productId);
    }


    public List<ProductInCart> findAllProductsInCartByUser(final String userEmail) {
        final Map<Cart, Product> productsByUserEmail = cartDao.findProductsByUserEmail(new Email(userEmail));
        return productsByUserEmail.entrySet().stream()
                .map(entry -> new ProductInCart(
                        entry.getKey().getId(),
                        entry.getValue().getName(),
                        entry.getValue().getPrice(),
                        entry.getValue().getImageUrl()
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteCartItem(final String userEmail, final Long id) {
        final Cart cart = cartDao.findById(id);

        if (!cart.isCartOwner(new Email(userEmail))) {
            throw new UserForbiddenException("해당 장바구니의 사용자가 아닙니다.");
        }

        cartDao.delete(cart.getId());
    }
}
