package cart.service;

import cart.dao.CartDao;
import cart.dto.CartRequest;
import cart.entity.CartEntity;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  private final CartDao cartDao;

  public CartService(CartDao cartDao) {
    this.cartDao = cartDao;
  }

  public void addCart(final long memberId, final CartRequest cartRequest) {
    cartDao.save(new CartEntity(cartRequest.getProductId(), memberId));
  }
}
