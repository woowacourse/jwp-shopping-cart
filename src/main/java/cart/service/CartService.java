package cart.service;

import cart.dao.cart.CartDao;
import cart.dao.product.ProductDao;
import cart.domain.cart.Cart;
import cart.domain.product.Product;
import cart.dto.cart.RequestCartDto;
import cart.dto.cart.ResponseCartDto;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public void create(final Long memberId, final RequestCartDto requestCartDto) {
        final Cart cart = new Cart(requestCartDto.getProductId(), memberId);
        cartDao.insert(cart);
    }

    public List<ResponseCartDto> display(final Long memberId) {
        final List<Cart> carts = cartDao.findAll();
        return carts.stream()
                .filter(cart -> cart.getMemberId().equals(memberId))
                .map(this::joinCartAndProduct)
                .collect(Collectors.toList());
    }

    private ResponseCartDto joinCartAndProduct(final Cart cart) {
        final Product product = productDao.findByID(cart.getProductId()).orElseThrow(NoSuchElementException::new);
        final int quantity = cart.getQuantity().getValue();
        return ResponseCartDto.of(product, quantity);
    }
}
