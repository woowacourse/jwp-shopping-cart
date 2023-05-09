package cart.service;

import cart.dao.CartDao;
import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.entity.CartEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

  private final CartDao cartDao;

  public CartService(CartDao cartDao) {
    this.cartDao = cartDao;
  }

  public void addCart(final long memberId, final CartRequest cartRequest) {
    final long productId = cartRequest.getProductId();
    cartDao.findCartByMemberIdAndProductId(memberId, productId)
        .ifPresentOrElse(cart -> cartDao.addCartCount(cart.getCartCount()+1, memberId, productId),
            () -> cartDao.save(new CartEntity(productId, memberId)));
  }

  public List<CartResponse> findCartByMemberId(final long memberId) {
    final List<CartEntity> cartEntities = cartDao.findCartByMemberId(memberId);
    return cartEntities.stream()
        .map(cartEntity -> new CartResponse(cartEntity.getCartCount(), cartEntity.getProductId()))
        .collect(Collectors.toList());
  }

  public void deleteByMemberIdAndProductId(long memberId, long productId) {
    cartDao.deleteByMemberIdAndProductId(memberId, productId);
  }
}
