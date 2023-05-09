package cart.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import cart.dao.CartDao;
import cart.dto.CartRequest;
import cart.entity.CartEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CartServiceTest {

  private static final long productId = 1L;
  private static final long memberId = 1L;

  private CartService cartService;
  private CartDao cartDao;

  @BeforeEach
  void setUp() {
    cartDao = Mockito.mock(CartDao.class);
    cartService = new CartService(cartDao);
  }

  @Test
  void addExistedCart() {
    final CartEntity existedCart = new CartEntity(productId, memberId);
    given(cartDao.findCartByMemberIdAndProductId(memberId, productId)).willReturn(Optional.of(existedCart));

    cartService.addCart(memberId, new CartRequest(productId));

    verify(cartDao, times(1))
        .addCartCount(existedCart.getCartCount() + 1, memberId, productId);
  }

  @Test
  void addNonExistedCart() {
    given(cartDao.findCartByMemberIdAndProductId(memberId, productId)).willReturn(Optional.empty());

    cartService.addCart(memberId, new CartRequest(productId));

    verify(cartDao, times(1))
        .save(any());
  }
}
