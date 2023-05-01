package cart.service.cart;

import cart.config.auth.AuthMember;
import cart.domain.cart.CartId;
import cart.domain.product.ProductId;
import cart.service.response.ProductResponse;

import java.util.List;

public interface CartService {
    CartId addProduct(final AuthMember authMember, final ProductId productId);

    List<ProductResponse> findAllByMember(final AuthMember authMember);

    int removeProduct(final AuthMember authMember, final ProductId productId);
}
