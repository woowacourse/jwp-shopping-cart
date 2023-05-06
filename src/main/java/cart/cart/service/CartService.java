package cart.cart.service;

import cart.cart.domain.CartId;
import cart.common.auth.request.AuthMember;
import cart.product.domain.ProductId;
import cart.product.service.response.ProductResponse;

import java.util.List;

public interface CartService {
    CartId addProduct(final AuthMember authMember, final ProductId productId);

    List<ProductResponse> findAllByMember(final AuthMember authMember);

    int removeProduct(final AuthMember authMember, final ProductId productId);
}
