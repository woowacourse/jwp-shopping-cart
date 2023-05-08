package cart.repository;

import cart.service.dto.CartResponse;
import java.util.List;

public interface ShoppingCartRepository {

    void addProduct(long memberId, long productId);

    void removeProduct(long cartId);

    List<CartResponse> findAllProduct(long memberId);
}
